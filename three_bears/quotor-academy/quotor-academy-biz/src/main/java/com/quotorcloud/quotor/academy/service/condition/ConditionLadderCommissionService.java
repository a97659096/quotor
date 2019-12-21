package com.quotorcloud.quotor.academy.service.condition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionLadderCommission;

/**
 * <p>
 * 阶梯提成表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
public interface ConditionLadderCommissionService extends IService<ConditionLadderCommission> {

    Boolean saveConditionLadderCommission(ConditionLadderCommission conditionLadderCommission);

    Boolean updateConditionLadderCommission(ConditionLadderCommission conditionLadderCommission);

    Boolean removeConditionLadderCommission(String id);

    IPage listPageConditionLadderCommission(Page page, ConditionLadderCommission conditionLadderCommission);

}
