package com.quotorcloud.quotor.academy.service.impl.invitejob;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.quotorcloud.quotor.academy.api.dto.invitejob.InviteJobDTO;
import com.quotorcloud.quotor.academy.api.entity.invitejob.InviteJob;
import com.quotorcloud.quotor.academy.api.vo.invitejob.InviteJobVO;
import com.quotorcloud.quotor.academy.mapper.invitejob.InviteJobMapper;
import com.quotorcloud.quotor.academy.service.invitejob.InviteJobPositionService;
import com.quotorcloud.quotor.academy.service.invitejob.InviteJobService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 招聘下店信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-04
 */
@Service
public class InviteJobServiceImpl extends ServiceImpl<InviteJobMapper, InviteJob> implements InviteJobService {

    @Autowired
    private InviteJobMapper inviteJobMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Autowired
    private InviteJobPositionService inviteJobPositionService;

    @Override
    public Boolean saveInviteJob(InviteJobDTO inviteJobDTO) {
        shopSetterUtil.shopSetter(inviteJobDTO, inviteJobDTO.getShopId());
        InviteJob inviteJob = new InviteJob();
        BeanUtils.copyProperties(inviteJobDTO, inviteJob,
                "rangeDate", "ijIssueTime", "img");
        inviteJob.setDelState(CommonConstants.STATUS_NORMAL);
        //处理特殊字段
        manageSpecialField(inviteJobDTO, inviteJob);
        inviteJobMapper.insert(inviteJob);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateInviteJob(InviteJobDTO inviteJobDTO) {
        InviteJob inviteJob = new InviteJob();
        BeanUtils.copyProperties(inviteJobDTO,inviteJob, "rangeDate", "ijIssueTime", "img");
        //是否有需要删除的字段去删除,调用之前写好的删除方法
        FileUtil.removeFileAndField(inviteJob, inviteJobDTO, "img", FileConstants.FileType.FILE_INVITI_IMG_DIR);
        //处理特殊字段
        manageSpecialField(inviteJobDTO, inviteJob);
        inviteJobMapper.updateById(inviteJob);
        return Boolean.TRUE;
    }

    private void manageSpecialField(InviteJobDTO inviteJobDTO, InviteJob inviteJob) {
        //获取日期范围
        List<String> stringDate = DateTimeUtil.getStringDate(inviteJobDTO.getDateRange());
        //如果不为空则把值set进去
        if(!ComUtil.isEmpty(stringDate)){
            inviteJob.setStartDate(DateTimeUtil.stringToLocalDate(stringDate.get(0)));
            inviteJob.setEndDate(DateTimeUtil.stringToLocalDate(stringDate.get(1)));
        }
        //如果状态改为发布，那发布日期获取当前时间
        if(inviteJobDTO.getStatus().equals(CommonConstants.ISSUE)){
            inviteJob.setIssueTime(LocalDateTime.now());
        }

        //查询职位信息
        if(!ComUtil.isEmpty(inviteJob.getPositionId())){
            String positionName = inviteJobPositionService.getById(inviteJob.getPositionId()).getName();
            inviteJob.setPositionName(positionName);
        }
        //存储图片
        FileUtil.saveFileAndField(inviteJob, inviteJobDTO, "img", FileConstants.FileType.FILE_INVITI_IMG_DIR, null);
        //招聘，对店铺处理
        if(!ComUtil.isEmpty(inviteJob.getType())) {
            //获取用户信息
            QuotorUser user = SecurityUtils.getUser();
            if (inviteJob.getType().equals(CommonConstants.INVITE)) {
                //设置店铺标识
                inviteJob.setShopId(String.valueOf(user.getDeptId()));
            } else if (inviteJob.getType().equals(CommonConstants.INN)) {
                //设置下单人标识
                inviteJob.setXdrId(String.valueOf(user.getId()));
             }
        }
    }

    @Override
    public Boolean removeInviteJob(String id) {
        inviteJobMapper.deleteById(id);
        return Boolean.TRUE;
    }

    /**
     * 查询招聘或求职列表
     * @param page
     * @param inviteJobDTO
     * @return
     * @throws ParseException
     */
    @Override
    public IPage<InviteJobVO> listInviteJob(Page<InviteJobVO> page, InviteJobDTO inviteJobDTO) throws ParseException {
        //如果不为空，则需要按时间范围进行查询，进行转换并赋值
        if(!ComUtil.isEmpty(inviteJobDTO.getDateRange())){
            List<String> stringDate = DateTimeUtil.getStringDate(inviteJobDTO.getDateRange());
            inviteJobDTO.setStart(stringDate.get(0));
            inviteJobDTO.setEnd(stringDate.get(1));
        }
        //查询数据库
        IPage<InviteJobVO> inviteJobIPage;
        //当类型为招聘时
        if(inviteJobDTO.getType().equals(CommonConstants.INVITE)){
            inviteJobIPage = inviteJobMapper.listInviteJob(page, inviteJobDTO);
        //当类型为求职时
        }else if(inviteJobDTO.getType().equals(CommonConstants.INN)){
            inviteJobIPage = inviteJobMapper.listFindJob(page, inviteJobDTO);
        }else {
            return null;
        }
        //获取结果集
        List<InviteJobVO> records = inviteJobIPage.getRecords();
        //转换成VO
        for (InviteJobVO record : records) {
            mapVO(record);
        }
        return inviteJobIPage;
    }

    private void mapVO(InviteJobVO record) throws ParseException {
        //对时间进行特殊处理转换成时间戳
        String start = DateTimeUtil.dateToStamp(DateTimeUtil.localDateToString(record.getStartDate()));
        String end = DateTimeUtil.dateToStamp(DateTimeUtil.localDateToString(record.getEndDate()));
        //set进去
        List<Long> dateRange = Lists.newArrayList(Long.valueOf(start), Long.valueOf(end));
        record.setRangeDate(dateRange);
        record.setImg(FileUtil.getJsonObjects(record.getImgDatabase()));
    }

    /**
     * 按id查询
     * @param type
     * @param id
     * @return
     * @throws ParseException
     */
    @Override
    public InviteJobVO selectInviteJobById(Integer type, String id) throws ParseException {
        InviteJobVO inviteJobVO;
        if(type.equals(CommonConstants.INVITE)){
            inviteJobVO = inviteJobMapper.selectInviteJobById(id);
        }else if(type.equals(CommonConstants.INN)){
            inviteJobVO = inviteJobMapper.selectFindJobById(id);
        }else {
            return null;
        }
        mapVO(inviteJobVO);
        return inviteJobVO;
    }

}
