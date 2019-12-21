package com.quotorcloud.quotor.academy.api.vo.member;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCardDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberCardVO {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 会员标识
     */
    private String memberId;

    /**
     * 卡片标识
     */
    private String cardId;

    /**
     * 卡片类型标识
     */
    private String cardTypeId;

    /**
     * 卡片类型名称
     */
    private String cardTypeName;

    /**
     * 卡耗方式
     */
    private Integer cardTypeWay;

    /**
     * 卡片名称
     */
    private String cardName;

    /**
     * 卡片售价
     */
    private BigDecimal cardPrice;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 卡片是否过期，1过期，2未过期
     */
    private Integer expire;

    /**
     * 会员卡状态，1已激活，2未激活
     */
    private Integer status;

    /**
     * 会员卡来源，1购买，2赠送
     */
    private Integer cardSource;

    /**
     * 卡片开始日期
     */
    private LocalDate cardStartDate;

    /**
     * 卡片到期类型：1永久有效，2指定日期
     */
    private Integer cardExpireDateType;

    /**
     * 卡片到期时间
     */
    private LocalDate cardExpireDate;

    /**
     * 购买日期
     */
    private LocalDate buyDate;

    /**
     * 开卡店铺标识
     */
    private String shopId;

    /**
     * 开卡店铺名称
     */
    private String shopName;

    /**
     * 卡初始值
     */
    private BigDecimal cardInitial;

    /**
     * 卡余
     */
    private BigDecimal cardSurplus;

    /**
     * 卡余金额
     */
    private BigDecimal cardSurplusMoney;

    /**
     * 卡余次数
     */
    private Integer cardSurplusTimes;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    private List<MemberCardDetail> memberCardDetailList;

}
