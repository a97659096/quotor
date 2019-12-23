package com.quotorcloud.quotor.academy.api.vo.teacher;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class TeacherVO {


    private String id;
    /**
     * 头像
     */
    private String headImg;

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

    /**
     * 授课环境
     */
    private List<JSONObject> environment;

    /**
     * 上传护照
     */
    private List<JSONObject> passport;

}
