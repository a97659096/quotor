package com.quotorcloud.quotor.academy.api.vo.employee;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeAttendanceVO {

    private String attId;

    private String empUserId;

    private String empName;

    private String phone;

    private String headImg;

    private String week;

    /**
     * 是否可设置为正常，1可以，2不可以
     */
    private Integer updateAttState;

    private String empNumber;

    private String shopId;

    private String shopName;

    private LocalDate date;

    private String ruleId;

    private LocalDateTime signInTime;

    private LocalDateTime signOutTime;

    private Integer attState;

    private Integer overWorkTime;

}
