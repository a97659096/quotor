package com.quotorcloud.quotor.academy.api.entity.condition;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 卡片详情
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_card_detail")
public class ConditionCardDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "cd_id", type = IdType.UUID)
    private String id;

    /**
     * 卡片标识
     */
    @TableField(value = "cd_card_id")
    private String cardId;

    /**
     * 详情类型：1卡内容，2购卡赠送，3充值赠送
     */
    @TableField(value = "cd_detail_type")
    private Integer detailType;

    /**
     * 内容标识
     */
    @TableField(value = "cd_content_id")
    private String contentId;

    /**
     * 内容名称
     */
    @TableField(value = "cd_content_name")
    private String contentName;

    /**
     * 内容价格
     */
    @TableField(value = "cd_content_price")
    private BigDecimal contentPrice;

    /**
     * 内容
     */
    @TableField(value = "cd_content")
    private BigDecimal content;

    /**
     * 内容类型，1折扣，2金额，3次数，4卡片
     */
    @TableField(value = "cd_content_type")
    private Integer contentType;

//
//    /**
//     * 内容次数
//     */
//    @TableField(value = "cd_content_count") T
//    private Integer cdCount;

//    /**
//     * 内容折扣
//     */
//    private Integer cdDiscount;
//
//    /**
//     * 内容金额
//     */
//    private BigDecimal cdGiveMoney;

//    /**
//     * 充值满赠金额
//     */
//    private BigDecimal cdReachMoney;

    /**
     * 创建时间
     */
    @TableField(value = "cd_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "cd_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
