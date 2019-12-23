package com.quotorcloud.quotor.academy.service.impl.condition;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCategory;
import com.quotorcloud.quotor.academy.api.vo.tree.CategoryTree;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionCategoryMapper;
import com.quotorcloud.quotor.academy.service.condition.ConditionCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.TreeUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 品项类别信息维护 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-07
 */
@Service
public class ConditionCategoryServiceImpl extends ServiceImpl<ConditionCategoryMapper, ConditionCategory> implements ConditionCategoryService {

    @Autowired
    private ConditionCategoryMapper conditionCategoryMapper;

    /**
     * 新增类别
     * @param conditionCategory
     * @return
     */
    @Override
    public Boolean saveConditionCategory(ConditionCategory conditionCategory) {
        setShopInfo(conditionCategory);
        conditionCategory.setCDelState(CommonConstants.STATUS_NORMAL);
        conditionCategoryMapper.insert(conditionCategory);
        return Boolean.TRUE;
    }

    /**
     * 构建树结构
     * @return
     */
    @Override
    public JSONObject getCategoryTree(ConditionCategory conditionCategory) {
        //当店铺标识为空时，默认取当前用户所属的店铺标识，查当前用户的
        setShopInfo(conditionCategory);

        List<ConditionCategory> conditionCategories = conditionCategoryMapper.selectList(new QueryWrapper<ConditionCategory>()
                .eq(!ComUtil.isEmpty(conditionCategory.getCShopId()), "c_shop_id", conditionCategory.getCShopId())
                .eq("c_del_state", 0)
                .in(!ComUtil.isEmpty(conditionCategory.getTypes()), "c_type",conditionCategory.getTypes()));
        //构建Tree bean
        List<CategoryTree> categoryTrees = conditionCategories.stream().map(category -> {
            CategoryTree categoryTree = new CategoryTree();
            categoryTree.setName(category.getCName());
            categoryTree.setShopId(category.getCShopId());
            categoryTree.setType(category.getCType());
            categoryTree.setId(category.getId());
            categoryTree.setParentId(category.getCParentId());
            return categoryTree;
        }).collect(Collectors.toList());
        //构建树结构
        List<CategoryTree> trees = TreeUtil.buildByLoop(categoryTrees, "0");
        //根据type 分组显示
        Map<Integer, List<CategoryTree>> collect = trees.stream().collect(Collectors.groupingBy(CategoryTree::getType));
        JSONObject jsonObject = new JSONObject();
        //对Key值重新赋值
        for (Integer type:collect.keySet()){
            switch (type){
                case 1:
                    jsonObject.put("project", collect.get(type));
                    break;
                case 2:
                    jsonObject.put("product", collect.get(type));
                    break;
                case 3:
                    jsonObject.put("setMeal", collect.get(type));
                    break;
                case 4:
                    jsonObject.put("card", collect.get(type));
                    break;
                case 5:
                    jsonObject.put("ticket", collect.get(type));
                    break;
            }
        }
        return jsonObject;
    }

    //设置店铺信息
    private void setShopInfo(ConditionCategory conditionCategory) {
        if(ComUtil.isEmpty(conditionCategory.getCShopId())){
            QuotorUser user = SecurityUtils.getUser();
            conditionCategory.setCShopId(String.valueOf(user.getDeptId()));
        }
    }

    /**
     * 修改类别信息
     * @param conditionCategory
     * @return
     */
    @Override
    public Boolean updateCategory(ConditionCategory conditionCategory) {
        return this.updateById(conditionCategory);
    }

    /**
     * 删除类别信息
     * @param id
     * @return
     */
    @Override
    public Boolean removeCategory(String id) {
        ConditionCategory conditionCategory = new ConditionCategory();
        conditionCategory.setId(id);
        conditionCategory.setCDelState(CommonConstants.STATUS_DEL);
        conditionCategoryMapper.updateById(conditionCategory);
        return Boolean.TRUE;
    }

    /**
     * 寻找id下边所有子级的集合
     * @param categoryId
     * @return
     */
    public List<String> findCategoryIds(String categoryId) {
        List<String> categoryIds = new LinkedList<>();
        List<ConditionCategory> conditionCategories = this
                .list(new QueryWrapper<ConditionCategory>().eq("c_del_state", 0));

        List<CategoryTree> treeNode = conditionCategories.stream().map(conditionCategory -> {
            CategoryTree categoryTree = new CategoryTree();
            categoryTree.setId(conditionCategory.getId());
            categoryTree.setParentId(conditionCategory.getCParentId());
            return categoryTree;
        }).collect(Collectors.toList());
        TreeUtil.findChildrenNodeIdList(treeNode, categoryId, categoryIds);
        //最后把自己也追加上，构成一个自己和自己下边所有自己的一个集合
        categoryIds.add(categoryId);
        return categoryIds;
    }


}
