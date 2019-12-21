package com.quotorcloud.quotor.academy.api.entity.message;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信订单表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_message_order")
public class MessageOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mo_id", type = IdType.UUID)
    private String id;

    @TableField(value = "mo_open_id")
    private String openId;

    /**
     * 标题
     */
    @TableField(value = "mo_title")
    private String title;

    /**
     * 条数
     */
    @TableField(value = "mo_amount")
    private Integer amount;

    /**
     * 价格
     */
    @TableField(value = "mo_price")
    private BigDecimal price;

    /**
     * 订单唯一标识
     */
    @TableField(value = "mo_out_trade_no")
    private String outTradeNo;

    /**
     * 支付完成时间
     */
    @TableField(value = "mo_pay_time")
    private LocalDateTime payTime;

    /**
     * 购买人
     */
    @TableField(value = "mo_purchaser")
    private String purchaser;

    /**
     * 购买人标识
     */
    @TableField(value = "mo_purchaser_id")
    private String purchaserId;

    /**
     * 店铺标识
     */
    @TableField(value = "mo_shop_id")
    private String shopId;

    /**
     * 店铺名称
     */
    @TableField(value = "mo_shop_name")
    private String shopName;

    /**
     * 支付类型：1微信扫码，2支付宝扫码，3小程序
     */
    @TableField(value = "mo_pay_type")
    private Integer payType;

    /**
     * 支付状态，1成功，2失败
     */
    @TableField(value = "mo_pay_state")
    private Integer payState;

    /**
     * ip地址
     */
    @TableField(value = "mo_ip")
    private String ip;

    /**
     * 订单状态，0正常，1删除
     */
    @TableField(value = "mo_state")
    private Integer state;

    /**
     * 创建时间
     */
    @TableField(value = "mo_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mo_gmt_modified")
    private LocalDateTime gmtModified;


}
