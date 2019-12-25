package com.quotorcloud.quotor.academy.service.homepage;

import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;

import java.util.Map;

public interface WebHomePageService {

    /**
     * 计算首页总收入
     * @param orderDetailPay
     * @return
     */
    JSONObject countHomePageTotalIncome(OrderDetailPay orderDetailPay);

}
