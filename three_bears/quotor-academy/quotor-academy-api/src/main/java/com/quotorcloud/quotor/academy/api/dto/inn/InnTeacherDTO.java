package com.quotorcloud.quotor.academy.api.dto.inn;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class InnTeacherDTO {

    private String id;

    /**
     * 头像
     */
    private MultipartFile headImg;

    private String headImgString;
    /**
     * 姓名
     */
    private String teacherName;

    /**
     * 性别，1男，2女
     */
    private Integer sex;

    /**
     * 薪资
     */
    private BigDecimal salary;

    /**
     * 个人简介
     */
    private String intro;

}
