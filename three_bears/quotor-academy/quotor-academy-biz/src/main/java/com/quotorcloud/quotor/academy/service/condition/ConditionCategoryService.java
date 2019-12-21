package com.quotorcloud.quotor.academy.service.condition;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCategory;

import java.util.List;

/**
 * <p>
 * 品项类别信息维护 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-07
 */
public interface ConditionCategoryService extends IService<ConditionCategory> {

    Boolean saveConditionCategory(ConditionCategory conditionCategory);

    JSONObject getCategoryTree(ConditionCategory conditionCategory);

    Boolean updateCategory(ConditionCategory conditionCategory);

    Boolean removeCategory(String id);

    List<String> findCategoryIds(String id);

}
