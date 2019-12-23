package com.quotorcloud.quotor.academy.mapper.condition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionContentCommission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 项目产品卡片提成表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface ConditionContentCommissionMapper extends BaseMapper<ConditionContentCommission> {

    /**
     * 查询项目和产品的提成列表
     * @param page
     * @param conditionContentCommission
     * @return
     */
    IPage<ConditionContentCommission> listProCommissionPage(Page page, @Param("commission") ConditionContentCommission conditionContentCommission);

    /**
     * 查询卡片的提成列表
     * @param page
     * @param conditionContentCommission
     * @return
     */
    IPage<ConditionContentCommission> listCardCommissionPage(Page page, @Param("commission") ConditionContentCommission conditionContentCommission);

}
