package com.quotorcloud.quotor.academy.api.entity.invitejob;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 招聘下店信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_invite_job")
public class InviteJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 岗位标识
     */
    private String positionId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 福利
     */
    private String welfare;

    /**
     * 工资（日新） 始值
     */
    private BigDecimal salaryStart;

    /**
     * 工资 终值
     */
    private BigDecimal salaryEnd;

    /**
     * 地址
     */
    private String address;

    /**
     * 所属店铺标识
     */
    private String shopId;

    /**
     * 招聘状态，1发布，2下架，3待发布
     */
    private Integer status;

    /**
     * 岗位详情
     */
    private String positionDetail;

    /**
     * 技能要求
     */
    private String skillRequire;

    /**
     * 技能特长
     */
    private String skillSuper;

    /**
     * 个人详情
     */
    private String ownerDetail;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 发布时间
     */
    private LocalDateTime issueTime;

    /**
     * 类型，1招聘，求职
     */
    private Integer type;

    /**
     * 下店人员标识
     */
    private String xdrId;

    /**
     * 下店人员名称
     */
    private String xdrName;

    /**
     * 图片
     */
    private String img;


    @TableLogic
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime GmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime GmtModified;


}
