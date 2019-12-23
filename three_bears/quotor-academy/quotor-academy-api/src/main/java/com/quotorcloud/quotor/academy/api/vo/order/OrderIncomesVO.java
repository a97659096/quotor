package com.quotorcloud.quotor.academy.api.vo.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderIncomesVO {

    /**
     * 内容名称
     */
    private String contentName;
    /**
     * 金额
     */
    private BigDecimal orderMoney;
    /**
     * 员工名字
     */
    private String employeeName;
}
