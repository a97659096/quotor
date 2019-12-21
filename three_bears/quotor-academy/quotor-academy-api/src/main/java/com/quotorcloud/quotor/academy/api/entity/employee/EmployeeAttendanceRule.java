package com.quotorcloud.quotor.academy.api.entity.employee;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工考勤规则信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_employee_attendance_rule")
public class EmployeeAttendanceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "ear_id", type = IdType.UUID)
    private String id;

    /**
     * 规则名称
     */
    @TableField(value = "ear_name")
    private String name;

    /**
     * 考勤口号
     */
    @TableField(value = "ear_slogan")
    private String slogan;

    /**
     * 考勤类型：1按固定时间考勤，2按排班考勤
     */
    @TableField(value = "ear_type")
    private Integer type;

    /**
     * 开始时间
     */
    @TableField(value = "ear_start_time")
    private LocalTime startTime;

    /**
     * 结束时间
     */
    @TableField(value = "ear_end_time")
    private LocalTime endTime;

    /**
     * 工作日
     */
    @TableField(value = "ear_workday")
    private String workday;

    /**
     * 加班时间起算时间（几分钟后）
     */
    @TableField(value = "ear_start_overwork_time")
    private Integer startOverworkTime;

    /**
     * 非工作日是否计算为加班，1算入，0不算入
     */
    @TableField(value = "ear_not_workday")
    private Integer notWorkday;

    /**
     * 加班设置是否开启 1启用，2不启用
     */
    @TableField(value = "ear_overtime_start_use")
    private Integer overtimeStartUse;

    /**
     * 适用门店标识
     */
    @TableField(value = "ear_shop_id")
    private String shopId;

    @TableField(value = "ear_shop_name")
    private String shopName;

    /**
     * 适用员工：1全店通用，2部分员工
     */
    @TableField(value = "ear_apply_emp")
    private Integer applyEmp;

    /**
     * 如果选择部分员工，则把员工id集合存在此处
     */
    @TableField(value = "ear_emp_user_id_list")
    private String empUserIdList;

    /**
     * 添加人标识
     */
    @TableField(value = "ear_creater_id")
    private String createrId;

    /**
     * 设置人名称
     */
    @TableField(value = "ear_creater_name")
    private String createrName;

    @TableField(value = "ear_del_state")
    @TableLogic
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(value = "ear_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "ear_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
