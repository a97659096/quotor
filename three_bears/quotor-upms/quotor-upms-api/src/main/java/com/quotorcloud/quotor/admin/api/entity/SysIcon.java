package com.quotorcloud.quotor.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图标管理
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_icon")
public class SysIcon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 图标
     */
    private String icon;

    /**
     * 图标描述
     */
    private String description;

    /**
     * 图标状态，1启用，2停用
     */
    private Integer state;

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


}
