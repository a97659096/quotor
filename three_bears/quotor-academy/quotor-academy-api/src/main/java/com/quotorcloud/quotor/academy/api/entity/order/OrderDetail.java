package com.quotorcloud.quotor.academy.api.entity.order;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.quotorcloud.quotor.academy.api.entity.employee.OrderDetailServiceStaff;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "od_id", type = IdType.UUID)
    private String id;

    /**
     * 订单唯一标识
     */
    @TableField(value = "od_order_id")
    private String orderId;

    /**
     * 内容标识
     */
    @TableField(value = "od_content_id")
    private String contentId;

    /**
     * 内容名称
     */
    @TableField(value = "od_content_name")
    private String contentName;

    /**
     * 内容单价
     */
    @TableField(value = "od_content_price")
    private BigDecimal contentPrice;

    /**
     * 内容数量
     */
    @TableField(value = "od_content_quantity")
    private Integer contentQuantity;

    /**
     * 内容类型：1项目，2产品，3套餐，4卡片，5续充，6换卡升级，7买券
     */
    @TableField(value = "od_content_type")
    private Integer contentType;

    /**
     * 会员标识
     */
    @TableField(value = "od_member_id")
    private String memberId;

    /**
     * 会员类型，1是会员，2不是会员
     */
    @TableField(value = "od_member_type")
    private Integer memberType;

    /**
     * 小计
     */
    @TableField(value = "od_subtotal")
    private BigDecimal subtotal;

    /**
     * 应支付金额
     */
    @TableField(value = "od_pay_money")
    private BigDecimal payMoney;

    /**
     * 创建时间
     */
    @TableField(value = "od_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "od_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private List<OrderDetailPay> orderDetailPays;

    @TableField(exist = false)
    private List<OrderDetailServiceStaff> serviceStaffs;

}
