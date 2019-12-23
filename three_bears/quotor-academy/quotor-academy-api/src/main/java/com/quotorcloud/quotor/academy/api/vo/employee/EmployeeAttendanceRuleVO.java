package com.quotorcloud.quotor.academy.api.vo.employee;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class EmployeeAttendanceRuleVO {

    private String id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 考勤口号
     */
    private String slogan;

    /**
     * 考勤类型：1按固定时间考勤，2按排班考勤
     */
    private Integer type;

    /**
     * 开始时间
     */
    private LocalTime startTime;

    /**
     * 结束时间
     */
    private LocalTime endTime;

    /**
     * 工作日
     */
    private String workday;

    /**
     * 加班时间起算时间（几分钟后）
     */
    private Integer startOverworkTime;

    /**
     * 非工作日是否计算为加班，1算入，0不算入
     */
    private Integer notWorkday;

    /**
     * 加班设置是否开启 1启用，2不启用
     */
    private Integer overtimeStartUse;

    /**
     * 适用门店标识
     */
    private String shopId;

    /**
     * 适用门店名称
     */
    private String shopName;

    /**
     * 适用员工：1全店通用，2部分员工
     */
    private Integer applyEmp;

    /**
     * 如果选择部分员工，则把员工id集合存在此处
     */
    private String empUserIdList;

    /**
     * 添加人标识
     */
    private String createrId;

    /**
     * 设置人名称
     */
    private String createrName;

    private LocalDateTime gmtCreate;
}
