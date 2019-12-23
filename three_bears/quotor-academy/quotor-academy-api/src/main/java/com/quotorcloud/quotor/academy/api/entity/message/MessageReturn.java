package com.quotorcloud.quotor.academy.api.entity.message;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 短信退还记录
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_message_return")
@Builder
public class MessageReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mr_id", type = IdType.UUID)
    private String id;

    /**
     * 发送时间
     */
    @TableField(value = "mr_send_time")
    private LocalDateTime sendTime;

    /**
     * 短信内容
     */
    @TableField(value = "mr_content")
    private String content;

    /**
     * 退还条数
     */
    @TableField(value = "mr_return_count")
    private Integer returnCount;

    /**
     * 手机号
     */
    @TableField(value = "mr_phone")
    private String phone;

    /**
     * 退还时间
     */
    @TableField(value = "mr_return_time")
    private LocalDateTime returnTime;

    /**
     * 姓名
     */
    @TableField(value = "mr_name")
    private String name;

    /**
     * 所属店铺标识
     */
    @TableField(value = "mr_shop_id")
    private String shopId;

    /**
     * 所属店铺名称
     */
    @TableField(value = "mr_shop_name")
    private String shopName;

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
