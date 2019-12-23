package com.quotorcloud.quotor.academy.api.dto.course;


import lombok.Data;

@Data
public class CourseOrderDTO {

    /**
     * 唯一标识
     */
    private String id;

    private String name;

    private String phone;

    private String courseId;

    private Integer userId;

    private String code;

    private Integer pageNo;

    private Integer pageSize;

    private String courseName;

    private String teacherId;

    private String teacherName;

    private Integer courseType;

    private String dateRange;

    private Integer studyState;

    private String start;

    private String end;

    private Integer status;

}
