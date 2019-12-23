package com.quotorcloud.quotor.academy.api.vo.appoint;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AppointVO {

    /**
     * 唯一标识
     */
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
     * 预约人
     */
    private String memberName;

    /**
     * 预约人电话
     */
    private String memberPhone;

    /**
     * 会员编号
     */
    private String memberNumber;

    /**
     * 会员头像
     */
    private String memberHeadImg;

    /**
     * 人数
     */
    private String population;

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
     * 店铺标识
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 预约状态
     */
    private Integer appointState;

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

}
