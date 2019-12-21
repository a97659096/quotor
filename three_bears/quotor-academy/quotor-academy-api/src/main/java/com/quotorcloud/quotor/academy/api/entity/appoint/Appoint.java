package com.quotorcloud.quotor.academy.api.entity.appoint;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 预约信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_appoint")
public class Appoint implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 预约标识
     */
    private String appointId;

    /**
     * 预约人标识
     */
    private String memberId;

    /**
     * 人数
     */
    private Integer population;

    /**
     * 预约日期
     */
    private LocalDate appointDate;

    /**
     * 流水单号
     */
    private String wasteBook;

    /**
     * 是否是新客，1是，2不是
     */
    private Integer newCustomer;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预约状态，1待确认，2生效中，3已完成，4失效
     */
    private Integer appointState;

    /**
     * 所属店铺
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 预约服务人员标识
     */
    private String appointStaffId;

    /**
     * 预约服务人员姓名
     */
    private String appointStaffName;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 项目信息，用  标识：次数  表示一个，多个之间用逗号隔开
     */
    private String projects;

    /**
     * 房间标识
     */
    private String roomId;

    /**
     * 房间名称
     */
    private String roomName;

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

}
