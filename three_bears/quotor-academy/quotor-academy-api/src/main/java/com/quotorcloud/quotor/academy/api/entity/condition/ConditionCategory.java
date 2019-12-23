package com.quotorcloud.quotor.academy.api.entity.condition;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 品项类别信息维护
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_category")
public class ConditionCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "c_id", type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    private String cName;

    /**
     * 父级标识
     */
    private String cParentId;

    /**
     * 维护类型：1:项目类别，2:产品类型，3:套餐，4:卡片，5:劵
     */
    private Integer cType;

    private String cShopId;

    private Integer cDelState;


    @TableField(exist = false)
    private List<Integer> types;

    /**
     * 创建时间
     */
    @TableField(value = "c_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "c_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
