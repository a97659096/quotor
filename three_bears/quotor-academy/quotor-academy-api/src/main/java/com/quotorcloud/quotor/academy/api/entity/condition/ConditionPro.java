package com.quotorcloud.quotor.academy.api.entity.condition;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_pro")
public class ConditionPro implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "p_id", type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    private String pName;

    /**
     * 适用门店标识
     */
    @TableField(value = "p_shop_id")
    private String shopId;

    /**
     * 适用门店名称
     */
    @TableField(value = "p_shop_name")
    private String shopName;

    /**
     * 统一售价
     */
    private BigDecimal pUnifiedPrice;

    /**
     * 项目时长
     */
    private Integer pTimeLength;

    /**
     * 类别标识
     */
    private String pCategoryId;

    /**
     * 类别名称
     */
    private String pCategoryName;

    /**
     * 手机端是否可预约，1:可预约，2:不可预约
     */
    private Integer cSubscribe;

    /**
     * 编号
     */
    private String pNumber;

    /**
     * 项目体验价
     */
    private BigDecimal pExperiencePrice;

    /**
     * 描述
     */
    private String pDescribe;

    /**
     * 状态，1可用，2不可用
     */
    private Integer pState;

    /**
     * 项目配图
     */
    private String pImg;

    /**
     * 删除状态，0正常，1删除
     */
    private Integer pDelState;

    /**
     * 产品类型
     */
    private Integer pProductType;

    /**
     * 产品单位
     */
    private String pProductUnit;

    /**
     * 产品品牌
     */
    private String pProductBrand;

    /**
     * 产品形态
     */
    private Integer pProductForm;

    /**
     * 产品容量
     */
    private String pProductCapacity;

    /**
     * 产品保质期
     */
    private String pProductExpirDate;

    /**
     * 类型，1项目，2产品
     */
    private Integer pType;

    /**
     * 创建时间
     */
    @TableField(value = "p_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "p_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
