package com.quotorcloud.quotor.academy.api.api.entity;

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
 * 支付方式表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_order_pay_way")
public class OrderPayWay implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "opw_id", type = IdType.UUID)
    private String id;

    /**
     * 支付方式名称
     */
    private String name;

    /**
     * 支付方式标识
     */
    private String payId;

    /**
     * 类别，1业绩，2卡耗，3非业绩非卡耗
     */
    private Integer type;

    /**
     * 来源
     */
    private String source;

    /**
     * 状态1启用，2停用
     */
    private Integer state;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


}
