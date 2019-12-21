package com.quotorcloud.quotor.academy.api.entity.member;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 积分规则
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_member_integral_rule")
public class MemberIntegralRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mir_id", type = IdType.UUID)
    private String id;

    /**
     * 规则名称
     */
    @TableField(value = "mir_name")
    private String name;

    /**
     * 适用会员，1全部客户，2持卡客户
     */
    @TableField(value = "mir_apply_member")
    private Integer applyMember;

    /**
     * 规则类型，1现金消费产生积分，3老会员拉新产生积分，4客户评价产生积分
     */
    @TableField(value = "mir_rule_type")
    private Integer ruleType;

    /**
     * 消费金额
     */
    @TableField(value = "mir_consumer_money")
    private BigDecimal consumerMoney;

    /**
     * 赠送积分
     */
    @TableField(value = "mir_give_integral")
    private Integer giveIntegral;

    /**
     * 1永久有效，2指定日期
     */
    @TableField(value = "mir_effect_date_type")
    private Integer effectDateType;

    /**
     * 生效日期
     */
    @TableField(value = "mir_effect_date")
    private LocalDate effectDate;

    /**
     * 所属店铺标识
     */
    @TableField(value = "mir_shop_id")
    private String shopId;

    /**
     * 所属店铺名称
     */
    @TableField(value = "mir_shop_name")
    private String shopName;

    /**
     * 创建时间
     */
    @TableField(value = "mir_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mir_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
