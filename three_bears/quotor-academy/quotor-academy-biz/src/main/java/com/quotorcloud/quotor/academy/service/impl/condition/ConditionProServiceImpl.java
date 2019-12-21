package com.quotorcloud.quotor.academy.service.impl.condition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.api.dto.condition.ConditionProDTO;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCategory;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionPro;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionSetMealDetail;
import com.quotorcloud.quotor.academy.api.vo.condition.ConditionProVO;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionProMapper;
import com.quotorcloud.quotor.academy.service.condition.ConditionCategoryService;
import com.quotorcloud.quotor.academy.service.condition.ConditionProService;
import com.quotorcloud.quotor.academy.service.condition.ConditionSetMealDetailService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-08
 */
@Service
public class ConditionProServiceImpl extends ServiceImpl<ConditionProMapper, ConditionPro> implements ConditionProService {

    @Autowired
    private ConditionProMapper conditionProMapper;

    @Autowired
    private ConditionCategoryService conditionCategoryService;

    @Autowired
    private ConditionSetMealDetailService setMealDetailService;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Override
    public String treeProByCategory(ConditionCategory conditionCategory) {
        String shopId = conditionCategory.getCShopId();
        List<Integer> types = conditionCategory.getTypes();
        //查询出项目和产品的树结构列表
        List<ConditionCategory> categoryTreeList = conditionCategoryService
                .list(new QueryWrapper<ConditionCategory>()
                .eq(!ComUtil.isEmpty(shopId),
                        "c_shop_id", shopId)
                .eq(!ComUtil.isEmpty(conditionCategory.getTypes()),
                        "p_type", conditionCategory.getTypes()));

        List<TreeNode> treeNodes = categoryTreeList.stream().map(categoryTree -> {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(categoryTree.getId());
            treeNode.setName(categoryTree.getCName());
            treeNode.setParentId(categoryTree.getCParentId());
            return treeNode;
        }).collect(Collectors.toList());
        List<TreeNode> treeNodeList = TreeUtil.buildByLoop(treeNodes, "0");

        //查询出所有项目产品套餐信息
        List<ConditionPro> conditionPros = conditionProMapper
                .selectList(new QueryWrapper<ConditionPro>()
                        .in(!ComUtil.isEmpty(types),"p_type", types));

        //根据品项类别分类
        Map<String, List<ConditionPro>> map =
                conditionPros.stream().collect(Collectors.groupingBy(ConditionPro::getPCategoryId));
        //把项目产品或者套餐追加到类别里
        setProInTree(treeNodeList, map);

        PropertyFilter filter = (o,s,o1) -> !(o1 instanceof List) || ((List) o1).size() != 0;
        return JSON.toJSONString(treeNodeList, filter);
    }

    //把项目放到树结构中并且把无产品的级别设为false
    private void setProInTree(List<TreeNode> treeNodeList, Map<String, List<ConditionPro>> map) {
        if(ComUtil.isEmpty(treeNodeList)){
            return;
        }
        for (TreeNode treeNode:treeNodeList){
            List<ConditionPro> conditionProList = map.get(treeNode.getId());
            //如果此类别下的产品不为空，则追加进children子级中
            if(!ComUtil.isEmpty(conditionProList)){
                //对产品进行遍历赋值
                List<TreeNode> treeNodes = conditionProList.stream().map(conditionPro -> {
                    TreeNode tree = new TreeNode();
                    tree.setId(conditionPro.getId());
                    tree.setPro(Boolean.TRUE);
                    tree.setName(conditionPro.getPName());
                    return tree;
                }).collect(Collectors.toList());
                //追加到子级中
                treeNode.getChildren().addAll(treeNodes);
            //不是项目，并且子级为空
            }else if (!treeNode.getPro() && ComUtil.isEmpty(conditionProList)
                    && ComUtil.isEmpty(treeNode.getChildren())){
                treeNode.setDisabled(Boolean.TRUE);
            }
            setProInTree(treeNode.getChildren(), map);
        }
    }

    /**
     * 新增产品或者项目
     * @param conditionProDTO
     * @return
     */
    @Override
    public Boolean saveConditionPro(ConditionProDTO conditionProDTO) {
        ConditionPro conditionPro = new ConditionPro();
        mapDTOToDO(conditionProDTO, conditionPro);
        conditionPro.setPDelState(CommonConstants.STATUS_NORMAL);
        conditionProMapper.insert(conditionPro);
        //为套餐时，去判断是否有传入套餐内容，并进行插入操作
        if(conditionProDTO.getPType().equals(CommonConstants.CONDITION_SET_MEAL)) {
            List<ConditionSetMealDetail> mealDetails = conditionProDTO.getMealDetails();
            if (!ComUtil.isEmpty(mealDetails)) {
                mealDetails.forEach(meal -> meal.setSmdProId(conditionPro.getId()));
                setMealDetailService.saveBatch(mealDetails);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateConditionPro(ConditionProDTO conditionProDTO) {
        ConditionPro conditionPro = conditionProMapper.selectById(conditionProDTO.getId());
        mapDTOToDO(conditionProDTO, conditionPro);
        //imgString不为空删除
        FileUtil.removeFileAndField(conditionPro, conditionProDTO, "pImg", FileConstants.FileType.FILE_CONDITION_PRO_IMG_DIR);
        conditionProMapper.updateById(conditionPro);
        return Boolean.TRUE;
    }

    //DTO DO 映射 供新增和修改使用
    private ConditionPro mapDTOToDO(ConditionProDTO conditionProDTO, ConditionPro conditionPro) {
        BeanUtils.copyProperties(conditionProDTO,conditionPro, "pImg", "pImgString", "pCategoryId");

        //設置店鋪信息
        shopSetterUtil.shopSetter(conditionPro, conditionProDTO.getShopId());

        //如果类别id不为空，查询出名称set进去
        List<String> pCategoryIds = conditionProDTO.getCategoryIds();
        //新增的时候传的数组，我取他的最后一个
        if(!ComUtil.isEmpty(pCategoryIds)){
            ConditionCategory categoryServiceOne = conditionCategoryService.getOne(new QueryWrapper<ConditionCategory>().eq("c_id",
                    pCategoryIds.get(pCategoryIds.size()-1)));
            conditionPro.setPCategoryId(pCategoryIds.get(pCategoryIds.size()-1));
            conditionPro.setPCategoryName(categoryServiceOne.getCName());
        }
        //图片文件夹名称
        String subfolder = null;
        if(conditionProDTO.getPType().equals(CommonConstants.CONDITION_PROJECT)){
            subfolder = "project/";
        }else if(conditionProDTO.getPType().equals(CommonConstants.CONDITION_PRODUCT)){
            subfolder = "product/";
        }else if(conditionProDTO.getPType().equals(CommonConstants.CONDITION_SET_MEAL)){
            subfolder = "meal/";
        }

        //保存图片
        FileUtil.saveFileAndField(conditionPro,conditionProDTO,
                "pImg",FileConstants.FileType.FILE_CONDITION_PRO_IMG_DIR, subfolder);
        return conditionPro;
    }

    /**
     * 查询产品、项目列表
     * @param conditionProDTO
     * @return
     */
    @Override
    public JSONObject listConditionPro(ConditionProDTO conditionProDTO) {
        Page<ConditionPro> page = PageUtil.getPage(conditionProDTO.getPageNo(), conditionProDTO.getPageSize());
        //查父级跟着查出子级id集合再set进去
        if(!ComUtil.isEmpty(conditionProDTO.getPCategoryId())){
            //查询id集合
            List<String> categoryIds = conditionCategoryService.findCategoryIds(conditionProDTO.getPCategoryId());
            conditionProDTO.setCategoryIds(categoryIds);
        }

        shopSetterUtil.shopSetter(conditionProDTO, conditionProDTO.getShopId());

        //查询数据
        IPage<ConditionProVO> conditionProVOIPage = conditionProMapper.selectProPage(page, conditionProDTO);

        //给图片地址重新赋值
        List<ConditionProVO> records = conditionProVOIPage.getRecords();
        for (ConditionProVO conditionProVO :records){
            if(!ComUtil.isEmpty(conditionProVO.getPImg())){
                conditionProVO.setPImg(FileUtil.getJsonObjects(conditionProVO.getImg()));
            }
        }
        //不让数组大小为0的继续返回
        PropertyFilter filter = (o,s,o1) -> !(o1 instanceof List) || ((List) o1).size() != 0;
        String result = JSON.toJSONString(records, filter);
        return PageUtil.getPagePackage("conditionPros", JSON.parse(result), page);
    }


    /**
     * 按id查询信息
     * @param id
     * @return
     */
    @Override
    public ConditionProVO selectProById(String id) {
        ConditionProVO conditionProVO = new ConditionProVO();
        ConditionPro conditionPro = conditionProMapper.selectById(id);
        BeanUtils.copyProperties(conditionPro, conditionProVO, "pImg");
        FileUtil.addPrefix(conditionPro, ConditionProVO.class, conditionProVO, "pImg");
        return conditionProVO;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Boolean removeConditionPro(String id) {
        ConditionPro conditionPro = new ConditionPro();
        conditionPro.setId(id);
        conditionPro.setPDelState(CommonConstants.STATUS_DEL);
        conditionProMapper.updateById(conditionPro);
        return Boolean.TRUE;
    }


}
