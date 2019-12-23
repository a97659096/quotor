package com.quotorcloud.quotor.academy.api.entity.employee;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "e_id", type = IdType.UUID)
    private String id;

    /**
     * 姓名
     */
    @TableField(value = "e_name")
    private String name;

    /**
     * 性别，1男，2女，3其他
     */
    @TableField(value = "e_sex")
    private Integer sex;

    /**
     * 联系电话
     */
    @TableField(value = "e_phone")
    private String phone;

    /**
     * 邮箱地址
     */
    @TableField(value = "e_email")
    private String email;

    /**
     * 入职时间
     */
    @TableField(value = "e_join_date")
    private LocalDate joinDate;

    /**
     * 员工花名
     */
    @TableField(value = "e_alias")
    private String alias;

    /**
     * 所属门店标识
     */
    @TableField(value = "e_shop_id")
    private String shopId;

    /**
     * 所属门店名称
     */
    @TableField(value = "e_shop_name")
    private String shopName;

    /**
     * 员工职位标识
     */
    @TableField(value = "e_position_id")
    private String positionId;

    /**
     * 员工职位名称
     */
    @TableField(value = "e_position_name")
    private String positionName;

    /**
     * 员工头衔标识
     */
    @TableField(value = "e_head_title_id")
    private String headTitleId;

    /**
     * 员工头衔名称
     */
    @TableField(value = "e_head_title_name")
    private String headTitleName;

    /**
     * 员工工号
     */
    @TableField(value = "e_job_number")
    private String jobNumber;

    /**
     * 员工微信号
     */
    @TableField(value = "e_wechat_number")
    private String wechatNumber;

    /**
     * 员工身份证号
     */
    @TableField(value = "e_identity_card")
    private String identityCard;

    /**
     * 头像
     */
    @TableField(value = "e_head_img")
    private String headImg;

    /**
     * 住址
     */
    @TableField(value = "e_address")
    private String address;

    /**
     * 紧急联系人
     */
    @TableField(value = "e_emergency_contact")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @TableField(value = "e_emer_phone")
    private String emerPhone;

    /**
     * 个人技能
     */
    @TableField(value = "e_skill")
    private String skill;

    /**
     * 个人描述
     */
    @TableField(value = "e_describe")
    private String description;

    /**
     * 员工作品集
     */
    @TableField(value = "e_works")
    private String works;

    /**
     * 在职状态：1在职，2离职
     */
    @TableField(value = "e_work_state")
    private Integer workState;

    /**
     * 是否可预约，1可以0不可以
     */
    @TableField(value = "e_subscribe")
    private Integer subscribe;

    /**
     * 预约排序
     */
    @TableField(value = "e_sort")
    private Integer sort;

    @TableField(value = "e_del_state")
    private Integer delState;

    /**
     * 备注
     */
    @TableField(value = "e_remark")
    private String remark;

    /**
     * 用户标识
     */
    @TableField(value = "e_user_id")
    private String userId;

    /**
     * 创建时间
     */
    @TableField(value = "e_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "e_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
