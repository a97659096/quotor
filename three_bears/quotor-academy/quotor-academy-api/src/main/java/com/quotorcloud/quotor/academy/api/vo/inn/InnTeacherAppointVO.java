package com.quotorcloud.quotor.academy.api.vo.inn;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InnTeacherAppointVO {

    private String id;

    private String openId;

    private String shopId;

    private String shopName;

    private String shopHeadImg;

    private LocalDate startDate;

    private LocalDate endDate;

    private String remark;

    private String outTradeNo;

    private BigDecimal totalFee;

    /**
     * 支付回调时间
     */
    private LocalDateTime notifyTime;

    /**
     * 支付状态，1成功，2未支付
     */
    private Integer payState;

    /**
     * 支付类型：1微信扫码，2支付宝扫码，3小程序
     */
    private Integer payType;

    private String innTeacherId;

    private BigDecimal innTeacherSalary;

    private String innTeacherName;

    private String ip;

    private LocalDateTime payTime;
}
