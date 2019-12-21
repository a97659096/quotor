package com.quotorcloud.quotor.academy.api.dto.course;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class TeacherDTO {

    /**
     * 标识
     */
    private String id;

    /**
     * 头像
     */
    private MultipartFile headImg;

    private String headImgString;

    /**
     * 讲师姓名
     */
    private String name;

    /**
     * 性别：1男，2女，3其他
     */
    private Integer sex;

    /**
     * 讲师国籍
     */
    private String nationality;

    /**
     * 年龄
     */
    private String age;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 从业时长
     */
    private String workTime;

    /**
     * 个人技能
     */
    private String skill;

    /**
     * 讲师头衔
     */
    private String title;

    /**
     * 讲师简介
     */
    private String intro;

    /**
     * 住址
     */
    private String address;

    /**
     * 曾获奖项
     */
    private String prize;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 个人履历
     */
    private String experience;

    private Integer jobState;

    private Integer delState;

    /**
     * 授课环境
     */
    private MultipartFile[] environment;

    private List<String> environmentString;

    /**
     * 上传护照
     */
    private MultipartFile[] passport;

    private List<String> passportString;

    /**
     * 第几页
     */
    private Integer pageNo;

    /**
     * 当前页显示的条数
     */
    private Integer pageSize;

}
