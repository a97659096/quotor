package com.quotorcloud.quotor.academy.api.entity.member;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员卡信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_member_card")
public class MemberCard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mc_id", type = IdType.UUID)
    private String id;

    /**
     * 会员标识
     */
    @TableField(value = "mc_member_id")
    private String memberId;

    /**
     * 卡片标识
     */
    @TableField(value = "mc_card_id")
    private String cardId;

    /**
     * 卡片类型标识
     */
    @TableField(value = "mc_card_type_id")
    private String cardTypeId;

    /**
     * 卡片类型名称
     */
    @TableField(value = "mc_card_type_name")
    private String cardTypeName;

    /**
     * 卡耗方式
     */
    @TableField(value = "mc_card_type_way")
    private Integer cardTypeWay;

    /**
     * 卡片名称
     */
    @TableField(value = "mc_card_name")
    private String cardName;

    /**
     * 卡片售价
     */
    @TableField(value = "mc_card_price")
    private BigDecimal cardPrice;

    /**
     * 卡号
     */
    @TableField(value = "mc_card_number")
    private String cardNumber;

    /**
     * 订单号
     */
    @TableField(value = "mc_order_number")
    private String orderNumber;

    /**
     * 卡片是否过期，1过期，2未过期
     */
    @TableField(value = "mc_expire")
    private Integer expire;

    /**
     * 会员卡状态，1已激活，2未激活
     */
    @TableField(value = "mc_status")
    private Integer status;

    /**
     * 会员卡来源，1购买，2赠送
     */
    @TableField(value = "mc_card_source")
    private Integer cardSource;

    /**
     * 卡片开始日期
     */
    @TableField(value = "mc_card_start_date")
    private LocalDate cardStartDate;

    /**
     * 卡片到期类型：1永久有效，2指定日期
     */
    @TableField(value = "mc_card_expire_date_type")
    private Integer cardExpireDateType;

    /**
     * 卡片到期时间
     */
    @TableField(value = "mc_card_expire_date")
    private LocalDate cardExpireDate;

    /**
     * 购买日期
     */
    @TableField(value = "mc_buy_date")
    private LocalDate buyDate;

    /**
     * 开卡店铺标识
     */
    @TableField(value = "mc_shop_id")
    private String shopId;

    /**
     * 开卡店铺名称
     */
    @TableField(value = "mc_shop_name")
    private String shopName;

    /**
     * 卡初始值
     */
    @TableField(value = "mc_card_initial")
    private BigDecimal cardInitial;

    /**
     * 卡余
     */
    @TableField(value = "mc_card_surplus")
    private BigDecimal cardSurplus;

    /**
     * 创建时间
     */
    @TableField(value = "mc_gmt_create",fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mc_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 会员id集合
     */
    @TableField(exist = false)
    private List<String> memberIds;


}
