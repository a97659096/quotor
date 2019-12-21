package com.quotorcloud.quotor.academy.api.vo.employee;

import lombok.Data;

@Data
public class EmployeeAttendanceStatisticsVO {

    private String empName;

    private String empNumber;

    private Integer attDays;

    private Integer normal;

    private Integer beLate;

    private Integer leaveEarly;

    private Integer lackAtt;

    private Integer overWork;
}
