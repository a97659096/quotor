package com.quotorcloud.quotor.academy.api.entity.order;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "o_id", type = IdType.UUID)
    private String id;

    /**
     * 流水单号
     */
    @TableField(value = "o_expend_number")
    private String expendNumber;

    /**
     * 订单编号
     */
    @TableField(value = "o_order_number")
    private String orderNumber;

    /**
     * 房间标识
     */
    @TableField(value = "o_room_id")
    private String roomId;

    /**
     * 房间名称
     */
    @TableField(value = "o_room_name")
    private String roomName;

    /**
     * 会员到店次数
     */
    @TableField(value = "o_member_arrive")
    private Integer memberArrive;

    /**
     * 散客到店次数
     */
    @TableField(value = "o_not_member_arrive")
    private Integer notMemberArrive;

    /**
     * 备注信息
     */
    @TableField(value = "o_remark")
    private String remark;

    /**
     * 客户标识
     */
    @TableField(value = "o_member_id")
    private String memberId;

    /**
     * 店铺标识
     */
    @TableField(value = "o_shop_id")
    private String shopId;

    /**
     * 店铺名称
     */
    @TableField(value = "o_shop_name")
    private String shopName;

    /**
     * 开单时间
     */
    @TableField(value = "o_kd_time")
    private LocalDateTime kdTime;

    /**
     * 收银时间
     */
    @TableField(value = "o_collect_money_time")
    private LocalDateTime collectMoneyTime;

    /**
     * 是否是补单
     */
    @TableField(value = "o_replacement_order")
    private Integer replacementOrder;

    /**
     * 补单时间
     */
    @TableField(value = "o_replacement_time")
    private LocalDateTime replacementOrderTime;

    /**
     * 订单状态：1已付款，2未付款，3尾款单
     */
    @TableField(value = "o_status")
    private Integer status;

    /**
     * 订单类型：1综合，2购卡，3卡续充，4还卡升级，5购劵
     */
    @TableField(value = "o_type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField(value = "o_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "o_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private List<OrderDetail> orderDetails;

//    @TableField(exist = false)
//    private List<MemberCardDetail> cardDetailsContent;
//
//    @TableField(exist = false)
//    private List<MemberCardDetail> cardDetailsGive;

}
