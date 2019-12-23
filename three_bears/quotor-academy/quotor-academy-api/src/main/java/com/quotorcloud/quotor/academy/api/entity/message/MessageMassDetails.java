package com.quotorcloud.quotor.academy.api.entity.message;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信群发详情
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_message_mass_details")
public class MessageMassDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mmd_id", type = IdType.UUID)
    private String mmdId;

    /**
     * 群发标识
     */
    @TableField(value = "mmd_mass_id")
    private String massId;

    /**
     * 接收人电话
     */
    @TableField(value = "mmd_receive_phone")
    private String receivePhone;

    /**
     * 接收人姓名
     */
    @TableField(value = "mmd_receive_name")
    private String receiveName;

    /**
     * 接收状态，1成功，2失败
     */
    @TableField(value = "mmd_receive_state")
    private Integer receiveState;

    @TableField(value = "mmd_send_id")
    private String sendId;

    @TableField(value = "mmd_shop_id")
    private String shopId;

    /**
     * 创建时间
     */
    @TableField(value = "mmd_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mmd_gmt_modified")
    private LocalDateTime gmtModified;


}
