package com.quotorcloud.quotor.academy.api.entity.order;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_order_log")
public class OrderLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "ol_id", type = IdType.UUID)
    private String id;

    /**
     * 订单编号
     */
    @TableField(value = "ol_order_no")
    private String orderNo;

    /**
     * 类别
     */
    @TableField(value = "ol_type")
    private String type;

    /**
     * 操作事件
     */
    @TableField(value = "ol_operational_event")
    private String operationalEvent;

    /**
     * 操作人
     */
    @TableField(value = "ol_operational_user_id")
    private String operationUserId;

    /**
     * 所属店铺标识
     */
    @TableField(value = "ol_shop_id")
    private String shopId;

    /**
     * 所属店铺名称
     */
    @TableField(value = "ol_shop_name")
    private String shopName;

    /**
     * 创建时间
     */
    @TableField(value = "ol_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "ol_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
