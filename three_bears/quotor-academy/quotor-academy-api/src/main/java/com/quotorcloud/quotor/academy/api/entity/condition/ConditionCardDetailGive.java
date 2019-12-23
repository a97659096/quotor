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
 * 卡赠送详情
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_card_detail_give")
public class ConditionCardDetailGive implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "cpd_id", type = IdType.UUID)
    private String cpdId;

    /**
     * 卡片详情标识
     */
    private String cpdCdId;

    /**
     * 内容标识
     */
    private String cdContentId;

    /**
     * 内容名称
     */
    private String cdName;

    /**
     * 内容价格
     */
    private BigDecimal cdPrice;

    /**
     * 内容次数
     */
    private Integer cdCount;

    /**
     * 赠送金额
     */
    private BigDecimal cdGiveMoney;

    /**
     * 创建时间
     */
    @TableField(value = "cpd_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "cpd_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
