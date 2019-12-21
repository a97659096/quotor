package com.quotorcloud.quotor.academy.api.entity.course;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程订单表（课程预约）
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_course_order")
public class CourseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 预约人姓名
     */
    private String name;

    /**
     *
     * 预约人电话
     */
    private String phone;

    /**
     * 微信用户标识
     */
    private String openId;

    /**
     * 订单唯一标识
     */
    private String outTradeNo;

    /**
     * 支付状态
     */
    private Integer payState;

    /**
     * 支付回调时间
     */
    private LocalDateTime notifyTime;

    /**
     * 订单状态，1已完成，2待付款，3待评价，4已关闭
     */
    private Integer orderState;

    /**
     * 金额
     */
    private Integer totalFee;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 课程标识
     */
    private String courseId;

    /**
     * 用户标识
     */
    private Integer userId;

    /**
     * 支付类型：1微信，2支付宝
     */
    private Integer payType;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 学习状态：1待学习，2学习中，3已结束
     */
    private Integer studyState;

    /**
     * 删除状态，0正常，1删除
     */
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    @TableField(exist = false, fill = FieldFill.INSERT_UPDATE)
    private String courseName;


}
