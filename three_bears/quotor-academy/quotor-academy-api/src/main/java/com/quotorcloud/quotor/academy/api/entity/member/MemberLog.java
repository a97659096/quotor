package com.quotorcloud.quotor.academy.api.entity.member;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户护理日志
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_member_log")
public class MemberLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 员工标识
     */
    private String employeeId;

    //员工姓名
    private String employeeName;

    //服务内容
    private String serviceContent;

    /**
     * 会员标识
     */
    private String memberId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 相关图片
     */
    private String img;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
