package com.quotorcloud.quotor.academy.api.entity.teacher;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 讲师信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "t_id", type = IdType.UUID)
    private String id;

    /**
     * 头像
     */
    @TableField(value = "t_head_img")
    private String headImg;

    /**
     * 讲师姓名
     */
    @TableField(value = "t_name")
    private String name;

    /**
     * 性别：1男，2女，3其他
     */
    @TableField(value = "t_sex")
    private Integer sex;

    /**
     * 讲师国籍
     */
    @TableField(value = "t_nationality")
    private String nationality;

    /**
     * 年龄
     */
    @TableField(value = "t_age")
    private String age;

    /**
     * 身份证号
     */
    @TableField(value = "t_id_card")
    private String idCard;

    /**
     * 从业时长
     */
    @TableField(value = "t_work_time")
    private String workTime;

    /**
     * 个人技能
     */
    @TableField(value = "t_skill")
    private String skill;

    /**
     * 讲师头衔
     */
    @TableField(value = "t_title")
    private String title;

    /**
     * 讲师简介
     */
    @TableField(value = "t_intro")
    private String intro;

    /**
     * 住址
     */
    @TableField(value = "t_address")
    private String address;

    /**
     * 曾获奖项
     */
    @TableField(value = "t_prize")
    private String prize;

    /**
     * 联系电话
     */
    @TableField(value = "t_phone")
    private String phone;

    /**
     * 个人履历
     */
    @TableField(value = "t_experience")
    private String experience;

    @TableField(value = "t_environment")
    private String environment;

    @TableField(value = "t_passport")
    private String passport;

    @TableField(value = "t_job_state")
    private Integer jobState;

    @TableField(value = "t_del_state")
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(value = "t_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "t_gmt_modified")
    private LocalDateTime gmtModified;


}
