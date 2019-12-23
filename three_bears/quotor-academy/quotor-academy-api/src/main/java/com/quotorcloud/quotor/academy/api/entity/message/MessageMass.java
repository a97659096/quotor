package com.quotorcloud.quotor.academy.api.entity.message;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import javafx.scene.chart.ValueAxis;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信群发记录表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_message_mass")
public class MessageMass implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mm_id", type = IdType.UUID)
    private String id;

    @TableField(value = "mm_template_id")
    private String templateId;

    /**
     * 短信内容
     */
    @TableField(value = "mm_content")
    private String content;

    /**
     * 接收人数
     */
    @TableField(value = "mm_receive_count")
    private Integer receiveCount;

    /**
     * 提交时间
     */
    @TableField(value = "mm_submit_time")
    private LocalDateTime submitTime;

    /**
     * 发送时间
     */
    @TableField(value = "mm_send_time")
    private LocalDateTime sendTime;

    /**
     * 短信数量
     */
    @TableField(value = "mm_message_count")
    private Integer messageCount;

    /**
     * 审核状态，1成功，2失败
     */
    @TableField(value = "mm_check_state")
    private Integer checkState;

    /**
     * 发送状态：1成功，2失败
     */
    @TableField(value = "mm_send_state")
    private Integer sendState;

    /**
     * 所属店铺
     */
    @TableField(value = "mm_shop_id")
    private String shopId;

    /**
     * 所属店铺名称
     */
    @TableField(value = "mm_shop_name")
    private String shopName;

    /**
     * 创建时间
     */
    @TableField(value = "mm_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mm_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
