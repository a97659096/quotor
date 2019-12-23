package com.quotorcloud.quotor.academy.api.dto.employee;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工业绩DTO
 */
@Data
public class EmployeePerformanceDTO {

    private LocalDate startDate;

    private LocalDate endDate;

    private String employeeId;

    private List<String> employeeIds;

    private String shopId;

    private String shopName;

}
