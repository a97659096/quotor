package com.quotorcloud.quotor.academy.service.impl.expend;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.quotorcloud.quotor.academy.api.dto.expend.ExpendDTO;
import com.quotorcloud.quotor.academy.api.entity.expend.Expend;
import com.quotorcloud.quotor.academy.api.entity.expend.ExpendType;
import com.quotorcloud.quotor.academy.api.vo.expend.ExpendVO;
import com.quotorcloud.quotor.academy.mapper.expend.ExpendMapper;
import com.quotorcloud.quotor.academy.service.expend.ExpendService;
import com.quotorcloud.quotor.academy.service.expend.ExpendTypeService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import com.quotorcloud.quotor.common.core.util.PageUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 支出信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-01
 */
@Service
public class ExpendServiceImpl extends ServiceImpl<ExpendMapper, Expend> implements ExpendService {

    @Autowired
    private ExpendMapper expendMapper;

    @Autowired
    private ExpendTypeService expendTypeService;

    private static final String AFFIX = "affix/";

    /**
     * 新增支出
     * @param expendDTO
     * @return
     */
    @Override
    public Boolean saveExpend(ExpendDTO expendDTO) {
        Expend expend = new Expend();

        BeanUtils.copyProperties(expendDTO, expend,
                "eImg", "eEmployeeNameList");

        expend.setEDelState(CommonConstants.STATUS_NORMAL);
        if(!ComUtil.isEmpty(expendDTO.getEImg())){
            String affixName = FileUtil.saveFile(expendDTO.getEImg(),
                    FileConstants.FileType.FILE_EXPEND_IMG_DIR, AFFIX);
            expend.setEImg(affixName);
        }

        if(!ComUtil.isEmpty(expendDTO.getEEmployeeNameList())){
            String empNameList = Joiner.on(CommonConstants.SEPARATOR).join(expendDTO.getEEmployeeNameList());
            expend.setEEmployeeNameList(empNameList);
        }

        //设置店铺信息
        setShopInfo(expend);

        //支出类型赋值
        if(!ComUtil.isEmpty(expendDTO.getEExpendTypeId())){
            ExpendType expendType = expendTypeService.getById(expendDTO.getEExpendTypeId());
            expend.setEExpendTypeName(expendType.getName());
        }
        expendMapper.insert(expend);
        return Boolean.TRUE;
    }

    /**
     * 查询支出
     * @param expendDTO
     * @return
     */
    @Override
    public JSONObject listExpend(ExpendDTO expendDTO) {
        //设置日期
        if(!ComUtil.isEmpty(expendDTO.getDateRange())){
            List<String> stringDate = DateTimeUtil.getStringDate(expendDTO.getDateRange());
            if(!ComUtil.isEmpty(stringDate)){
                expendDTO.setStart(stringDate.get(0));
                expendDTO.setEnd(stringDate.get(1));
            }
        }
        //设置店铺信息
        if(ComUtil.isEmpty(expendDTO.getEShopId())){
            QuotorUser user = SecurityUtils.getUser();
            if(ComUtil.isEmpty(user)){
                return null;
            }
            expendDTO.setEShopId(String.valueOf(user.getDeptId()));
        }

        Page<Expend> page = PageUtil.getPage(expendDTO.getPageNo(), expendDTO.getPageSize());
        IPage<Expend> expendIPage = expendMapper.selectExpendPage(page, expendDTO);
        List<ExpendVO> expendVOS = mapDOAndVo(expendIPage.getRecords());
        return PageUtil.getPagePackage("expends", expendVOS, page);
    }

    private List<ExpendVO> mapDOAndVo(List<Expend> records) {
        List<ExpendVO> expendVOS = Lists.newArrayList();
        for(Expend expend : records){
            ExpendVO expendVO = new ExpendVO();
            BeanUtils.copyProperties(expend, expendVO, "eImg", "eEmployeeNameList");
            if(!ComUtil.isEmpty(expend.getEEmployeeNameList())){
                expendVO.setEEmployeeNameList(Splitter.on(CommonConstants.SEPARATOR)
                        .splitToList(expend.getEEmployeeNameList()));
            }
            FileUtil.addPrefix(expend, ExpendVO.class, expendVO, "eImg");
            expendVOS.add(expendVO);
        }
        return expendVOS;
    }

    /**
     * 删除支出
     * @param id
     * @return
     */
    @Override
    public Boolean removeExpend(String id) {
        Expend expend = new Expend();
        expend.setId(id);
        expend.setEDelState(1);
        expendMapper.updateById(expend);
        return Boolean.TRUE;
    }

    /**
     * 修改支出
     * @param expendDTO
     * @return
     */
    @Override
    public Boolean updateExpend(ExpendDTO expendDTO) {
        if(ComUtil.isEmpty(expendDTO.getId())){
            throw new MyException(ExceptionEnum.NOT_FIND_ID);
        }
        Expend expend = expendMapper.selectById(expendDTO.getId());

        BeanUtils.copyProperties(expendDTO, expend, "eImgString","eImg");
        //设置店铺信息
        setShopInfo(expend);

        FileUtil.removeFileAndField(expend, expendDTO, "eImg", FileConstants.FileType.FILE_EXPEND_IMG_DIR);

        FileUtil.saveFileAndField(expend, expendDTO, "eImg", FileConstants.FileType.FILE_EXPEND_IMG_DIR,null);
        expendMapper.updateById(expend);
        return Boolean.TRUE;
    }

    //设置店铺信息
    private void setShopInfo(Expend expend){
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return;
        }
        expend.setEShopId(String.valueOf(user.getDeptId()));
        expend.setEShopName(String.valueOf(user.getDeptName()));

    }

    @Override
    public ExpendVO selectExpendById(String id) {
        Expend expend = expendMapper.selectById(id);
        if(ComUtil.isEmpty(expend)){
            return null;
        }
        ExpendVO expendVO = new ExpendVO();
        BeanUtils.copyProperties(expend, expendVO, "eImg");
        FileUtil.addPrefix(expend, ExpendVO.class, expendVO, "eImg");
        return expendVO;
    }

    /**
     * 计算总支出金额
     * @param expendDTO
     * @return
     */
    @Override
    public JSONObject countTotalExpend(ExpendDTO expendDTO) {
        List<Expend> expends = expendMapper.selectExpendPage(expendDTO);
        BigDecimal totalExpend = expends.stream().map(Expend::getEMoney).reduce(BigDecimal::add).get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalExpend", totalExpend);
        return jsonObject;
    }

    /**
     * 按年 月 周查找小程序报表 1 周 2 月 3 年
     * @param date
     * @return
     */
    public JSONObject selectWXStatement(Integer dateType) {
        ExpendDTO expendDTO = new ExpendDTO();
        switch (dateType){
            case 1:
                expendDTO.setStart(DateTimeUtil.getWeekStartDate());
                break;
            case 2:
                expendDTO.setAppointDate(DateTimeUtil.localDateToString(LocalDate.now()).substring(0,7));
                break;
            case 3:
                expendDTO.setAppointDate(DateTimeUtil.localDateToString(LocalDate.now()).substring(0,4));
                break;
        }
        List<Expend> expends = expendMapper.selectExpendPage(expendDTO);
        JSONObject jsonObject = new JSONObject();
        List<String> x = new LinkedList<>();
        List<BigDecimal> y = new LinkedList<>();
        BigDecimal totalMoney = new BigDecimal(0);
        if(dateType.equals(1) || dateType.equals(2)){
            //分组根据日期
            Map<String, List<Expend>> map = new HashMap<>();
            for (Expend expend:expends){
                String date = DateTimeUtil.localDatetimeToString(expend.getGmtCreate());
                if(map.keySet().contains(date)){
                    map.get(date).add(expend);
                }else {
                    List<Expend> expendList = Lists.newArrayList(expend);
                    map.put(date, expendList);
                }
            }
            Set<String> dates = DateTimeUtil.getOrderByDate(map.keySet());
            for (String date: dates){
                BigDecimal money = map.get(date).stream().map(Expend::getEMoney).reduce(BigDecimal::add).get();
                x.add(date);
                y.add(money);
                totalMoney = totalMoney.add(money);
            }
        }else {
            //分组根据月份
            Map<String, List<Expend>> map = new HashMap<>();
            for (Expend expend:expends){
                String date = DateTimeUtil.localDatetimeToMonth(expend.getGmtCreate());
                if(map.keySet().contains(date)){
                    map.get(date).add(expend);
                }else {
                    List<Expend> expendList = Lists.newArrayList(expend);
                    map.put(date, expendList);
                }
            }
            Set<String> strings = new HashSet<>(map.keySet());
            for (String date:strings){
                BigDecimal money = map.get(date).stream().map(Expend::getEMoney).reduce(BigDecimal::add).get();
                x.add(date);
                y.add(money);
                totalMoney = totalMoney.add(money);
            }
        }
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("total", totalMoney);
        return jsonObject;
    }

    /**
     * 查詢支出列表
     * @param expendDTO
     * @return
     */
    @Override
    public JSONObject listExpendApp(ExpendDTO expendDTO) {
        Page<Expend> page = PageUtil.getPage(expendDTO.getPageNo(), expendDTO.getPageSize());
        IPage<Expend> expendIPage = expendMapper.selectExpendPage(page, expendDTO);
        List<ExpendVO> expendVOS = mapDOAndVo(expendIPage.getRecords());
        //分组
        Map<String, List<ExpendVO>> map = new HashMap<>();
        for (ExpendVO expendVO:expendVOS){
            String date = DateTimeUtil.localDatetimeToString(expendVO.getGmtCreate());
            if(map.keySet().contains(date)){
                map.get(date).add(expendVO);
            }else {
                List<ExpendVO> expends = Lists.newArrayList(expendVO);
                map.put(date, expends);
            }
        }

        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonObjects = new ArrayList<>();
        BigDecimal totalMoney = new BigDecimal(0);
        for (String date:map.keySet()){
            List<ExpendVO> expendVOS1 = map.get(date);
            JSONObject json = new JSONObject();
            json.put("date", date);
            json.put("list", expendVOS1);
            BigDecimal money = expendVOS1.stream()
                    .map(ExpendVO::getEMoney).reduce(BigDecimal::add).get();
            json.put("money", money);
            totalMoney = totalMoney.add(money);
            jsonObjects.add(json);
        }
        jsonObject.put("totalMoney", totalMoney);
        jsonObject.put("list", jsonObjects);

        return jsonObject;
    }



}
