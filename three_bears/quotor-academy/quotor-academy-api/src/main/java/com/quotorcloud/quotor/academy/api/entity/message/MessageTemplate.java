package com.quotorcloud.quotor.academy.api.entity.message;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 短信模板内容
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_message_template")
public class MessageTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId(value = "mt_id", type = IdType.UUID)
    private String id;

    /**
     * 包含变量的模板内容
     */
    @TableField(value = "mt_submail_content")
    private String submailContent;

    @TableField(value = "mt_original_content")
    private String originalContent;

    /**
     * 删除状态，0正常，1删除
     */
    @TableField(value = "mt_del_state")
    @TableLogic
    private Integer delState;

    /**
     * 模板标识
     */
    @TableField(value = "mt_template_id")
    private String templateId;

    /**
     * 模板状态，1审核通过，2审核失败，3审核中
     */
    @TableField(value = "mt_check_state")
    private Integer checkState;

    /**
     * 通过时间
     */
    @TableField(value = "mt_pass_time")
    private LocalDateTime passTime;

    /**
     * 失败原因
     */
    @TableField(value = "mt_fail_cause")
    private String failCause;

    /**
     * 32位随机字符串
     */
    @TableField(value = "mt_token")
    private String token;

    /**
     * 数字签名
     */
    @TableField(value = "mt_signature")
    private String signature;

    /**
     * 回调触发时间
     */
    @TableField(value = "mt_trigger_time")
    private String triggerTime;

    /**
     * 模板类型标识
     */
    @TableField(value = "mt_category_id")
    private String categoryId;

    /**
     * 创建时间
     */
    @TableField(value = "mt_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mt_gmt_modified")
    private LocalDateTime gmtModified;

}
