package com.quotorcloud.quotor.academy.api.entity.appoint;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 房间信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_appoint_room")
public class AppointRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 序号
     */
    private Integer serialNumber;

    /**
     * 店铺标识
     */
    private String shopId;

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
