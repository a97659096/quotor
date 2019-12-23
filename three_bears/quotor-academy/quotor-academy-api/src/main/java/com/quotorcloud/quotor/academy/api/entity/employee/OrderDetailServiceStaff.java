package com.quotorcloud.quotor.academy.api.entity.employee;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.quotorcloud.quotor.academy.api.entity.order.OrderDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单服务人员
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_order_detail_service_staff")
public class OrderDetailServiceStaff implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "ss_id", type = IdType.UUID)
    private String id;

    /**
     * 订单详情标识
     */
    @TableField(value = "ss_order_detail_id")
    private String orderDetailId;

    /**
     * 员工标识
     */
    @TableField(value = "ss_employee_id")
    private String employeeId;

    /**
     * 员工姓名
     */
    @TableField(value = "ss_employee_name")
    private String employeeName;

    @TableField(value = "ss_employee_no")
    private String employeeNo;

    /**
     * 分配业绩
     */
    @TableField(value = "ss_performance")
    private BigDecimal performance;

    /**
     * 分配卡耗
     */
    @TableField(value = "ss_card_expend")
    private BigDecimal cardExpend;

    /**
     * 卡耗单位，1金额，2次数
     */
    @TableField(value = "ss_card_expend_unit")
    private Integer cardExpendUnit;

    /**
     * 分配提成
     */
    @TableField(value = "ss_commission")
    private BigDecimal commission;

    /**
     * 1分配提成，2阶梯提成
     */
    @TableField(value = "ss_commission_type")
    private Integer commissionType;

    @TableField(value = "ss_employee_position_id")
    private String employeePositionId;

    /**
     * 创建时间
     */
    @TableField(value = "ss_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "ss_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 订单内容
     */
    @TableField(exist = false)
    private OrderDetail orderDetail;


}
