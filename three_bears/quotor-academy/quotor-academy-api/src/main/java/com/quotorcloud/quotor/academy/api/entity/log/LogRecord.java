package com.quotorcloud.quotor.academy.api.entity.log;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日志记录表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_log_record")
public class LogRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "lr_id", type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    @TableField(value = "lr_name")
    private String name;

    /**
     * 类别
     */
    @TableField(value = "lr_opeartion_type")
    private String opeartionType;

    /**
     * 操作事件
     */
    @TableField(value = "lr_opeartion_event")
    private String opeartionEvent;

    /**
     * 操作人
     */
    @TableField(value = "lr_opeartion_user")
    private String opeartionUser;

    /**
     * 店铺名称
     */
    @TableField(value = "lr_shop_name")
    private String shopName;

    /**
     * 操作人所属店铺
     */
    @TableField(value = "lr_shop_id")
    private String shopId;

    /**
     * 日志类型，1会员操作，2品项操作，3课程操作，4讲师操作，5员工操作
     */
    @TableField(value = "lr_type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField(value = "lr_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "lr_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
