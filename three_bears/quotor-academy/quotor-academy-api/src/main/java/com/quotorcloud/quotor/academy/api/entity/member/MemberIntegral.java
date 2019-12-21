package com.quotorcloud.quotor.academy.api.entity.member;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 积分记录表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_member_integral")
public class MemberIntegral implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mi_id", type = IdType.UUID)
    private String id;

    /**
     * 客户标识
     */
    @TableField(value = "mi_member_id")
    private String memberId;

    /**
     * 客户名称
     */
    @TableField(value = "mi_member_name")
    private String memberName;

    /**
     * 积分规则标识
     */
    @TableField(value = "mi_rule_id")
    private String ruleId;

    /**
     * 积分规则名称
     */
    @TableField(value = "mi_rule_name")
    private String ruleName;

    /**
     * 规则类型，1现金消费产生积分，2老会员拉新产生积分，3客户评价产生积分
     */
    @TableField(value = "mi_rule_type")
    private Integer ruleType;

    /**
     * 内容
     */
    @TableField(value = "mi_content")
    private String content;

    /**
     * 产生积分
     */
    @TableField(value = "mi_integral")
    private Integer integral;

    /**
     * 操作人标识
     */
    @TableField(value = "mi_operator_id")
    private String operatorId;

    /**
     * 操作人名称
     */
    @TableField(value = "mi_operator_name")
    private String operatorName;

    /**
     * 创建时间
     */
    @TableField(value = "mi_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mi_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
