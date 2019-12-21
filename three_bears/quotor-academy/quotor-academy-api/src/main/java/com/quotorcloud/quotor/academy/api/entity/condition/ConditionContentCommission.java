package com.quotorcloud.quotor.academy.api.entity.condition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目产品卡片提成表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_content_commission")
public class ConditionContentCommission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "ccc_id", type = IdType.UUID)
    private String id;

    /**
     * 项目/产品标识
     */
    @TableField(value = "ccc_content_id")
    private String contentId;

    @TableField(exist = false)
    private String contentName;

    /**
     * 提成
     */
    @TableField(value = "ccc_commission")
    private BigDecimal commission;

    /**
     * 1折扣，2金额
     */
    @TableField(value = "ccc_commission_type")
    private Integer commissionType;

    /**
     * 职位标识
     */
    @TableField(value = "ccc_position_id")
    private String positionId;

    /**
     * 职位名称
     */
    @TableField(value = "ccc_position_name")
    private String positionName;

    /**
     * 创建时间
     */
    @TableField(value = "ccc_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "ccc_gmt_modified")
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private String shopId;

    @TableField(exist = false)
    private String categoryId;

    @TableField(exist = false)
    private Integer contentType;
}
