package com.quotorcloud.quotor.academy.service.condition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionContentCommission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 项目产品卡片提成表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface ConditionContentCommissionService extends IService<ConditionContentCommission> {

    Boolean setContentCommission(ConditionContentCommission conditionContentCommission);

    IPage<ConditionContentCommission> listContentCommission(Page page, ConditionContentCommission conditionContentCommission);

}
