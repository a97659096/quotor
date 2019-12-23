package com.quotorcloud.quotor.academy.api.dto.condition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCard;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ConditionCardDTO {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 卡模板类型标识
     */
    private String templateTypeId;

    /**
     * 卡模板类型名称
     */
    private String templateTypeName;

    //卡类型，1储值，2总次，3其他
    private Integer cardType;

    /**
     * 卡片名称
     */
    private String name;

    /**
     * 适用门店标识
     */
    private String shopId;

    /**
     * 适用门店名称
     */
    private String shopName;

    /**
     * 卡售价
     */
    private BigDecimal price;

    /**
     * 卡面额
     */
    private BigDecimal denomination;

    /**
     * 开始日期类型：1购卡时间，2首次使用时间，3指定日期
     */
    private Integer startDateType;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期类型：1不限时长，2固定时长，3指定日期
     */
    private Integer endDateType;

    /**
     * 固定时长长度，当结束日期类型为固定时长时需要用到
     */
    private Integer endDateDuration;

    /**
     * 时长单位，1年，2月，3日，当结束日期类型为固定时长时需要用到
     */
    private Integer endDateDurationUnit;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    /**
     * 卡类标识
     */
    private String categoryId;

    /**
     * 卡类名称
     */
    private String categoryName;

    /**
     * 售卡须知
     */
    private String sellNotice;

    /**
     * 卡状态：1启用，2停用
     */
    private Integer state;

    /**
     * 卡片的使用方式，1售卖，2赠送，3都可以
     */
    private Integer useWay;

    /**
     * 卡属性：1会员卡，2营销卡
     */
    private Integer property;

    /**
     * 开单限购：1不限次数，2限购
     */
    private Integer limitBuy;

    /**
     * 开单限购次数
     */
    private Integer limitBuyCount;


    /**
     * 卡包含内容
     */
    private List<ConditionCardDetail> cardContent;

    /**
     * 购卡赠送
     */
    private List<ConditionCardDetail> buyCardGive;

    /**
     * 充值赠送
     */
    private List<ConditionCardDetail> topUpGive;

    private List<String> categoryIds;
//
//    /**
//     * 时长(当结束类型为固定时长时，需要用到)
//     */
//    private Integer duration;
//
//    /**
//     * 单位(当结束类型为固定时长时，需要用到)
//     */
//    private Integer unit;

    private Integer pageNo;

    private Integer pageSize;

}
