package com.quotorcloud.quotor.academy.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_list_box")
//@Builder
public class ListBox {

    @TableId(value = "lb_id", type = IdType.UUID)
    private String id;

    @TableField(value = "lb_name")
    private String name;

    @TableField(value = "lb_tag")
    private String tag;

    @TableField(value = "lb_module")
    private String module;

    @TableField(value = "lb_shop_id")
    private String shopId;

    @TableField(value = "lb_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @TableField(value = "lb_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
