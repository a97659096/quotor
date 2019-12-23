package com.quotorcloud.quotor.academy.api.vo.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class OrderConsumeRecordVO {

    //消费时间
    private LocalDateTime consumeTime;

    //服务员工
    private String employeeNames;

    //订单编号
    private String orderNo;

    //消费内容
    private String consumeContent;

    //消费金额
    private BigDecimal consumeMoney;

    //消费次数
    private Integer consumeTimes;

    private Set<Integer> consumeWay;

    //订单标识
    private String orderId;

}
