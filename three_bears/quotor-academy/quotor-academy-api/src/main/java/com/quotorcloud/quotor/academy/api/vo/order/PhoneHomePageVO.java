package com.quotorcloud.quotor.academy.api.vo.order;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PhoneHomePageVO {

    /**
     * 卡耗金额
     */
    private BigDecimal cardConsumeMoney;

    /**
     * 卡耗次数
     */
    private Integer cardConsumeTimes;

    /**
     * 卡余金额
     */
    private BigDecimal cardSurplusMoney;

    /**
     * 卡余次数
     */
    private Integer cardSurplusTimes;

    /**
     * 日常支出
     */
    private BigDecimal dailyExpend;

    /**
     * 会员数
     */
    private Integer memberCount;

    /**
     * 今日收入
     */
    private BigDecimal todayIncome;

    /**
     * 收入详情
     */
    private List<JSONObject> incomeDetails;

}
