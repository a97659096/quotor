package com.quotorcloud.quotor.academy.api.entity.employee;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工考勤信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_employee_attendance")
public class EmployeeAttendance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "ea_id", type = IdType.UUID)
    private String id;

    /**
     * 用户标识
     */
    @TableField(value = "ea_user_id")
    private String userId;

    /**
     * 签到时间
     */
    @TableField(value = "ea_sign_in_time")
    private LocalDateTime signInTime;

    /**
     * 签退时间
     */
    @TableField(value = "ea_sign_out_time")
    private LocalDateTime signOutTime;

    /**
     * 考勤日期
     */
    @TableField(value = "ea_date")
    private LocalDate date;

    /**
     * 考勤规则标识
     */
    @TableField(value = "ea_attendance_rule_id")
    private String attendanceRuleId;

    /**
     * 考勤状态，1正常，2迟到，3早退，4缺卡，5其他
     */
    @TableField(value = "ea_attendance_state")
    private String attendanceState;

    /**
     * 加班时长（分钟）
     */
    @TableField(value = "ea_overtime")
    private String overtime;

    /**
     * 是否可设置打卡状态，1可以，2不可以
     */
    @TableField(value = "ea_update_att_state")
    private Integer updateAttState;

    /**
     * 创建时间
     */
    @TableField(value = "ea_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "ea_gmt_modified")
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private String dateRange;

    @TableField(exist = false)
    private String start;

    @TableField(exist = false)
    private String end;

    @TableField(exist = false)
    private String dateMonth;

    @TableField(exist = false)
    private String shopId;

}
