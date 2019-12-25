package com.quotorcloud.quotor.academy.api.entity.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付方式表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_order_pay_way")
public class OrderPayWay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "opw_id", type = IdType.UUID)
    private String id;

    /**
     * 支付方式名称
     */
    @TableField(value = "opw_name")
    private String name;

    /**
     * 支付方式标识
     */
    @TableField(value = "opw_pay_id")
    private String payId;

    /**
     * 类别，1业绩，2卡耗，3非业绩非卡耗
     */
    @TableField(value = "opw_type")
    private Integer type;

    /**
     * 来源
     */
    @TableField(value = "opw_source")
    private String source;

    /**
     * 颜色
     */
    @TableField(value = "opw_color")
    private String color;

    /**
     * 状态1启用，2停用
     */
    @TableField(value = "opw_state")
    private Integer state;

    /**
     * 创建时间
     */
    @TableField(value = "opw_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "opw_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
