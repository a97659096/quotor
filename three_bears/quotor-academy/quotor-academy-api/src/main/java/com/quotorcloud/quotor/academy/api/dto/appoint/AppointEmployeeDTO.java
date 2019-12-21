package com.quotorcloud.quotor.academy.api.dto.appoint;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AppointEmployeeDTO {

    private String employeeId;

    private String employeeName;

    private String employeeNo;

    private String positionName;

    private List<AppointDTO> appoints = new ArrayList<>();

}
