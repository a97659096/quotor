package com.quotorcloud.quotor.academy.api.vo.order;

import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderIncomeVO {

    /**
     * 支付方式标识
     */
    private String payWayId;

    /**
     * 支付方式名称
     */
    private String payWayName;

    /**
     * 支付记录
     */
    private BigDecimal money;

}
