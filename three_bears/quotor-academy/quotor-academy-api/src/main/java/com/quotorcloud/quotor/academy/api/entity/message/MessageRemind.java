package com.quotorcloud.quotor.academy.api.entity.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 短信提醒表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_remind")
public class MessageRemind implements Serializable {

    /**
     * 唯一标识
     */
    @TableId(value = "remind_id", type = IdType.UUID)
    private Integer remindId;

    /**
     * 提醒描述
     */
    @TableField(value = "remind_description")
    private String remindDescription;

    /**
     * 提醒状态
     */
    @TableField(value = "remind_status")
    private Integer remindStatus;

    /**
     * 提醒内容
     */
    @TableField(value = "remind_content")
    private String remindContent;

    /**
     * 提醒角色
     */
    @TableField(value = "remind_role")
    private Integer remindRole;


}
