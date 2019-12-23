package com.quotorcloud.quotor.academy.api.dto.appoint;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppointContent {

    private String appointStaffId;

    private String appointStaffName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String roomId;

    private String roomName;

    private List<AppointProject> appointProjects;
}
