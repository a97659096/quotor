package com.quotorcloud.quotor.academy.api.entity.member;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员卡详情表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_member_card_detail")
public class MemberCardDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mcd_id", type = IdType.UUID)
    private String id;

    /**
     * 会员卡标识
     */
    @TableField(value = "mcd_member_card_id")
    private String memberCardId;

    /**
     * 详情类型：1卡内容，2购卡赠送
     */
    @TableField(value = "mcd_detail_type")
    private Integer detailType;

    /**
     * 内容标识
     */
    @TableField(value = "mcd_content_id")
    private String contentId;

    /**
     * 内容名称
     */
    @TableField(value = "mcd_content_name")
    private String contentName;

    /**
     * 内容
     */
    @TableField(value = "mcd_content")
    private BigDecimal content;

    /**
     * 内容类型，1折扣，2金额，3总次，4分次
     */
    @TableField(value = "mcd_content_type")
    private Integer contentType;

    /**
     * 内容始值
     */
    @TableField(value = "mcd_content_initial")
    private BigDecimal contentInitial;

    /**
     * 内容余量
     */
    @TableField(value = "mcd_content_surplus")
    private BigDecimal contentSurplus;

    /**
     * 创建时间
     */
    @TableField(value = "mcd_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mcd_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
