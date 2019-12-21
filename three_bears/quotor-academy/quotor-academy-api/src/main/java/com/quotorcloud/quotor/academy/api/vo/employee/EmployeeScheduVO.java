package com.quotorcloud.quotor.academy.api.vo.employee;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeScheduVO {

    private String scheduId;

    private String title;

    private LocalDate start;

    private LocalDate end;

    private String color;

    private String classId;

}
