package com.quotorcloud.quotor.academy.api.entity.inn;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 下店老师信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_inn_teacher")
public class InnTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 头像
     */
    private String headImg;

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

    /**
     * 删除状态，0正常，1删除
     */
    @TableLogic
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


}
