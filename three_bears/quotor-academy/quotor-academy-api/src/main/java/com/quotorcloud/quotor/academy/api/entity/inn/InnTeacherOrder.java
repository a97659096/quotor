package com.quotorcloud.quotor.academy.api.entity.inn;

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
 * 下店订单表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_inn_teacher_order")
public class InnTeacherOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private String openId;

    /**
     * 申请店铺标识
     */
    private String shopId;

    /**
     * 申请人标识
     */
    private String userId;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 下店结束日期
     */
    private LocalDate endDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单编号
     */
    private String outTradeNo;

    /**
     * 金额
     */
    private BigDecimal totalFee;

    /**
     * 支付状态，1成功，2未支付
     */
    private Integer payState;

    /**
     * 支付完成时间
     */
    private LocalDateTime payTime;

    /**
     * 支付回调时间
     */
    private LocalDateTime notifyTime;

    /**
     * 订单状态：1已完成，2待付款，3待评价，4已关闭
     */
    private Integer orderState;

    /**
     * 支付类型：1微信扫码，2支付宝扫码，3小程序
     */
    private Integer payType;

    /**
     * 下店老师标识
     */
    private String innTeacherId;

    /**
     * 下店老师标识
     */
    private String innTeacherName;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 删除状态，0正常，1删除
     */
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    /**
     * 时间范围
     */
    @TableField(exist = false)
    private String dateRange;

    @TableField(exist = false)
    private String start;

    @TableField(exist = false)
    private String end;

}
