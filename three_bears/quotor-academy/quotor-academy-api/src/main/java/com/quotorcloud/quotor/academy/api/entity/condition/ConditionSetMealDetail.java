package com.quotorcloud.quotor.academy.api.entity.condition;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_set_meal_detail")
public class ConditionSetMealDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "smd_id", type = IdType.UUID)
    private String smdId;

    /**
     * 套餐或劵标识
     */
    private String smdProId;

    /**
     * 内容标识
     */
    private String smdContentId;

    /**
     * 内容名称
     */
    private String smdName;

    /**
     * 价格
     */
    private BigDecimal smdPrice;

    /**
     * 次数
     */
    private Integer smdNumber;

    private Integer smdDiscount;

    /**
     * 创建时间
     */
    @TableField(value = "smd_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "smd_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime smdGmtModified;


}
