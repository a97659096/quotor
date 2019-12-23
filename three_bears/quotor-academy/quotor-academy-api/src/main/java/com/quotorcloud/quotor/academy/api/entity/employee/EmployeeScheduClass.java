package com.quotorcloud.quotor.academy.api.entity.employee;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工排班班次信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_employee_schedu_class")
public class EmployeeScheduClass implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 班次标识颜色
     */
    private String color;

    /**
     * 班次名称
     */
    private String name;

    /**
     * 适用门店（门店标识）
     */
    private String shopId;

    /**
     * 创建人标识
     */
    private String createrId;

    /**
     * 创建人名称
     */
    private String createrName;

    /**
     * 工作时段
     */
    private String workTimeScope;

    /**
     * 0正常，1删除
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

    @TableField(exist = false)
    private Integer pageNo;

    @TableField(exist = false)
    private Integer pageSize;

}
