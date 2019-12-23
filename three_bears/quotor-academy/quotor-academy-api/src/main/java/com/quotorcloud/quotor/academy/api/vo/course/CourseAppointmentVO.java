package com.quotorcloud.quotor.academy.api.vo.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseAppointmentVO {

    private String id;

    private Integer totalFee;

    private Integer studyState;

    private String name;

    private String nickname;

    private String headImg;

    private String phone;

    private Integer type;

    private String courseName;

    private String teacherName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
