package com.quotorcloud.quotor.academy.api.vo.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @JSONField(name = "name")
    private String payWayName;

    /**
     * 支付记录
     */
    @JSONField(name = "value")
    private BigDecimal money;

    /**
     *颜色
     */
    @JSONField(serialize = false)
    private String color;


    private Object itemStyle;

}
