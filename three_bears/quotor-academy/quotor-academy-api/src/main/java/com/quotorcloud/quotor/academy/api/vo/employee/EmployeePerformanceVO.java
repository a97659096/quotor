package com.quotorcloud.quotor.academy.api.vo.employee;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 员工业绩
 */
@Data
@Builder
public class EmployeePerformanceVO {

    /**
     * 内容名称
     */
   private String contentName;

    /**
     * 内容标识
     */
   private String contentId;

    /**
     * 内容数量
     */
   private Integer contentTimes;

    /**
     * 内容类型
     */
   private String contentType;

    /**
     * 员工标识
     */
   private String employeeId;

    /**
     * 员工姓名
     */
   private String employeeName;

    /**
     * 员工头像
     */
   private String employeeHeadImg;

    /**
     * 员工工号
     */
   private String employeeJobNumber;

    /**
     * 员工性别
     */
   private Integer employeeSex;

    /**
     * 店铺标识
     */
   private String shopId;

    /**
     * 职位
     */
   private String employeePosition;

    /**
     * 员工业绩
     */
   private BigDecimal performance;

    /**
     * 员工卡耗次数
     */
   private Integer cardConsumeTimes;

    /**
     * 员工卡耗金额
     */
   private BigDecimal cardConsumeMoney;

    /**
     * 服务会员客数
     */
   private Integer serviceMemberTimes;

    /**
     * 服务散客客数
     */
   private Integer serviceNotMemberTimes;

    /**
     * 提成
     */
   private BigDecimal allotCommission;

    /**
     * 阶梯提成
     */
   private BigDecimal ladderCommission;

    /**
     * 开卡数
     */
   private Integer openCardTimes;

    /**
     * 项目数
     */
   private Integer projectTimes;

    /**
     * 产品数
     */
   private Integer productTimes;


}
