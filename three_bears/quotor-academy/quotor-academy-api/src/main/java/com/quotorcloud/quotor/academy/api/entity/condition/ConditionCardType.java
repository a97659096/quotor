package com.quotorcloud.quotor.academy.api.entity.condition;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 卡类型表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_card_type")
public class ConditionCardType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "cct_id", type = IdType.UUID)
    private String id;

    /**
     * 类型名称
     */
    @TableField(value = "cct_name")
    private String name;

    @TableField(value = "cct_type")
    private Integer type;

    /**
     * 卡耗方式，1折扣，2金额，3总次，4分次
     */
    @TableField(value = "cct_way")
    private Integer way;

    /**
     * 所属店铺标识
     */
    @TableField(value = "cct_shop_id")
    private String shopId;

    /**
     * 所属店铺名称
     */
    @TableField(value = "cct_shop_name")
    private String shopName;

    /**
     * 创建时间
     */
    @TableField(value = "cct_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "cct_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
