package com.quotorcloud.quotor.academy.api.entity.condition;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 卡片信息
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_card")
public class ConditionCard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "c_id", type = IdType.UUID)
    private String id;

    /**
     * 卡模板类型标识
     */
    @TableField(value = "c_template_type_id")
    private String templateTypeId;

    /**
     * 卡模板类型名称
     */
    @TableField(value = "c_template_type_name")
    private String templateTypeName;

    //卡类型，1储值，2总次，3其他
    @TableField(value = "c_card_type")
    private Integer cardType;

    /**
     * 卡片名称
     */
    @TableField(value = "c_name")
    private String name;

    /**
     * 适用门店标识
     */
    @TableField(value = "c_shop_id")
    private String shopId;

    /**
     * 适用门店名称
     */
    @TableField(value = "c_shop_name")
    private String shopName;

    /**
     * 卡售价
     */
    @TableField(value = "c_price")
    private BigDecimal price;

    /**
     * 卡面额
     */
    @TableField(value = "c_denomination")
    private BigDecimal denomination;

    /**
     * 开始日期类型：1购卡时间，2首次使用时间，3指定日期
     */
    @TableField(value = "c_start_date_type")
    private Integer startDateType;

    /**
     * 开始日期
     */
    @TableField(value = "c_start_date")
    private LocalDate startDate;

    /**
     * 结束日期类型：1不限时长，2固定时长，3指定日期
     */
    @TableField(value = "c_end_date_type")
    private Integer endDateType;

    /**
     * 固定时长长度，当结束日期类型为固定时长时需要用到
     */
    @TableField(value = "c_end_date_duration")
    private Integer endDateDuration;

    /**
     * 时长单位，1年，2月，3日，当结束日期类型为固定时长时需要用到
     */
    @TableField(value = "c_end_date_duration_unit")
    private Integer endDateDurationUnit;

    /**
     * 结束日期
     */
    @TableField(value = "c_end_date")
    private LocalDate endDate;

    /**
     * 卡类标识
     */
    @TableField(value = "c_category_id")
    private String categoryId;

    /**
     * 卡类名称
     */
    @TableField(value = "c_category_name")
    private String categoryName;

    /**
     * 售卡须知
     */
    @TableField(value = "c_sell_notice")
    private String sellNotice;

    /**
     * 卡状态：1启用，2停用
     */
    @TableField(value = "c_state")
    private Integer state;

    /**
     * 卡片的使用方式，1售卖，2赠送，3都可以
     */
    @TableField(value = "c_use_way")
    private Integer useWay;

    /**
     * 卡属性：1会员卡，2营销卡
     */
    @TableField(value = "c_property")
    private Integer property;

    /**
     * 开单限购：1不限次数，2限购
     */
    @TableField(value = "c_limit_buy")
    private Integer limitBuy;

    /**
     * 开单限购次数
     */
    @TableField(value = "c_limit_buy_count")
    private Integer limitBuyCount;

    /**
     * 是否删除，0正常，1删除
     */
    @TableField(value = "c_del_state")
    @TableLogic
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(value = "c_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "c_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
