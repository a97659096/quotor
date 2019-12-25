package com.quotorcloud.quotor.academy.api.entity.course;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "c_id", type = IdType.UUID)
    private String id;

    /**
     * 课程名称
     */
    @TableField(value = "c_name")
    private String name;

    /**
     * 课程内容
     */
    @TableField(value = "c_content")
    private String content;

    @TableField(value = "c_intro")
    private String intro;

    /**
     * 周期
     */
    @TableField(value = "c_period")
    private String period;

    /**
     * 课程图片
     */
    @TableField(value = "c_img")
    private String img;

    /**
     * 课程讲师标识
     */
    @TableField(value = "c_teacher_id")
    private String teacherId;

    /**
     * 课程讲师名称
     */
    @TableField(value = "c_teacher_name")
    private String teacherName;

    /**
     * 课程总票数
     */
    @TableField(value = "c_total_poll")
    private Integer totalPoll;

    /**
     * 课程剩余票数
     */
    @TableField(value = "c_surplus_poll")
    private Integer surplusPoll;

    /**
     * 加盟商价格
     */
    @TableField(value = "c_join_shop_price")
    private BigDecimal joinShopPrice;

    /**
     * 学员价格
     */
    @TableField(value = "c_student_price")
    private BigDecimal studentPrice;

    /**
     * 课程开始日期
     */
    @TableField(value = "c_start_date")
    private LocalDateTime startDate;

    /**
     * 课程结束日期
     */
    @TableField(value = "c_end_date")
    private LocalDateTime endDate;

    /**
     * 上课开始时间
     */
    @TableField(value = "c_start_time")
    private LocalTime startTime;

    /**
     * 上课结束时间
     */
    @TableField(value = "c_end_time")
    private LocalTime endTime;

    /**
     * 课程类型：1内部课程，2外部课程，3通用课程
     */
    @TableField(value = "c_type")
    private Integer type;

    /**
     * 课程状态：1已发布，2待发布，3已下架
     */
    @TableField(value = "c_status")
    private Integer status;

    /**
     * 课程发布时间
     */
    @TableField(value = "c_issue_time")
    private LocalDateTime issueTime;

    @TableField(value = "c_del_state")
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(value = "c_gmt_create",fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "c_gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private String schoolTime;


}
