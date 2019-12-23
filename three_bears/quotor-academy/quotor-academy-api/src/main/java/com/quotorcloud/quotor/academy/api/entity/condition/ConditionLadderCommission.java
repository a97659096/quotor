package com.quotorcloud.quotor.academy.api.entity.condition;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 阶梯提成表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_condition_ladder_commission")
public class ConditionLadderCommission implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 任务调度的参数key */
    @TableField(exist = false)
    public static final String JOB_PARAM_KEY    = "jobParam";

    /**
     * 唯一标识
     */
    @TableId(value = "clc_id", type = IdType.UUID)
    private String id;

    /**
     * 提成方案名称
     */
    @TableField(value = "clc_name")
    private String name;

    /**
     * 提成类型，1业绩，2卡耗
     */
    @TableField(value = "clc_commission_type")
    private Integer commissionType;

    /**
     * 业绩品项，1项目，2开卡，3产品
     */
    @TableField(value = "clc_condition")
    private String clcCondition;

    /**
     * 计算方式，1阶梯式计算，2阶段式计算
     */
    @TableField(value = "clc_calculate_way")
    private Integer calculateWay;

    /**
     * 结算周期，1按每周，2按每月，3按每季度，4按每年
     */
    @TableField(value = "clc_account_period")
    private Integer accountPeriod;

    /**
     * 适用职位，多个逗号隔开
     */
    @TableField(value = "clc_position_ids")
    private String positionIds;

    /**
     * 适用职位名称
     */
    @TableField(value = "clc_position_names")
    private String positionNames;

    /**
     * 适用门店标识
     */
    @TableField(value = "clc_shop_id")
    private String shopId;

    /**
     * 适用门店名称
     */
    @TableField(value = "clc_shop_name")
    private String shopName;

    /**
     * 提成规则设置，逗号隔开，1是折扣，2是金额 0-2222:10:1
     */
    @TableField(value = "clc_rule_set")
    private String ruleSet;

    /**
     * cron
     * 表达式
     */
    @TableField(value = "clc_cron")
    private String cron;

    @TableField(value = "clc_job_trigger")
    private String jobTrigger;

    /**
     * 创建时间
     */
    @TableField(value = "clc_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "clc_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
