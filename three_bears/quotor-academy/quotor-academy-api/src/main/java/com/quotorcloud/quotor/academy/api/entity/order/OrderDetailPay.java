package com.quotorcloud.quotor.academy.api.entity.order;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单付款详情
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_order_detail_pay")
public class OrderDetailPay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "odp_id", type = IdType.UUID)
    private String id;

    /**
     * 订单详情标识
     */
    @TableField(value = "odp_order_detail_id")
    private String orderDetailId;

    /**
     * 支付方式标识
     */
    @TableField(value = "odp_pay_way_id")
    private String payWayId;

    /**
     * 支付方式名称
     */
    @TableField(value = "odp_pay_way_name")
    private String payWayName;

    /**
     * 会员卡标识
     */
    @TableField(value = "odp_member_card_id")
    private String memberCardId;

    /**
     * 会员卡类型标识
     */
    @TableField(value = "odp_member_card_type_id")
    private String memberCardTypeId;

    /**
     * 会员卡类型
     */
    @TableField(value = "odp_member_card_type_name")
    private String memberCardTypeName;

    /**
     * 卡片标识
     */
    @TableField(value = "odp_card_id")
    private String cardId;

    /**
     * 付款金额
     */
    @TableField(value = "odp_pay_money")
    private BigDecimal payMoney;

    /**
     * 支付类型，1卡耗，2业绩，3非业绩非卡耗
     */
    @TableField(value = "odp_pay_type")
    private Integer payType;

    /**
     * 付款金额类型，1金额，2次数
     */
    @TableField(value = "odp_pay_money_type")
    private Integer payMoneyType;

    /**
     * 支付前
     */
    @TableField(value = "odp_pay_before")
    private BigDecimal payBefore;

    /**
     * 支付后
     */
    @TableField(value = "odp_pay_later")
    private BigDecimal payLater;

    /**
     * 店铺标识
     */
    @TableField(value = "odp_shop_id")
    private String shopId;

    /**
     * 创建时间
     */
    @TableField(value = "odp_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "odp_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 开始日期
     */
    private String start;

    /**
     * 结束日期
     */
    private String end;


}
