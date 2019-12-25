package com.quotorcloud.quotor.academy.service.impl.homepage;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.quotorcloud.quotor.academy.mapper.order.OrderDetailPayMapper;
import com.quotorcloud.quotor.academy.mapper.order.OrderMapper;
import com.quotorcloud.quotor.academy.service.homepage.WebHomePageService;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


@Service
public class WebHomePageServiceImpl implements WebHomePageService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailPayMapper orderDetailPayMapper;

    @Override
    public JSONObject countHomePageTotalIncome(OrderDetailPay orderDetailPay) {
        Integer dateType = orderDetailPay.getDateType();
        int days;
        switch (dateType){
            //近一月
            case 1:
                days = 30;
                break;
            //近三月
            case 2:
                days = 90;
                break;
            //近一年
            case 3:
                days = 365;
                break;
                default: days = 0;
        }
        orderDetailPay.setStart(DateTimeUtil.localDateToString(LocalDate.now().minusDays(days)));
        List<OrderDetailPay> orderDetailPays = orderDetailPayMapper.listOrderDetailPay(orderDetailPay);
        List<String> x = new LinkedList<>();
        List<BigDecimal> y = new LinkedList<>();
        BigDecimal totalMoney = new BigDecimal(0);
        Map<String, List<OrderDetailPay>> map = new HashMap<>();
        if(dateType.equals(1) || dateType.equals(2)){
            //分组根据日期
            for (OrderDetailPay detailPay: orderDetailPays){
                String date = DateTimeUtil.localDatetimeToString(detailPay.getGmtCreate());
                if(map.keySet().contains(date)){
                    map.get(date).add(detailPay);
                }else {
                    List<OrderDetailPay> expendList = Lists.newArrayList(detailPay);
                    map.put(date, expendList);
                }
            }
        }else {
            //分组根据月份
            for (OrderDetailPay detailPay:orderDetailPays){
                String date = DateTimeUtil.localDatetimeToMonth(detailPay.getGmtCreate());
                if(map.keySet().contains(date)){
                    map.get(date).add(detailPay);
                }else {
                    List<OrderDetailPay> expendList = Lists.newArrayList(detailPay);
                    map.put(date, expendList);
                }
            }
        }

        Set<String> strings = new TreeSet<>(map.keySet());
        for (String date:strings){
            BigDecimal money = map.get(date).stream()
                    .map(OrderDetailPay::getPayMoney).reduce(BigDecimal::add).get();
            x.add(date);
            y.add(money);
            totalMoney = totalMoney.add(money);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x",x);
        jsonObject.put("y",y);
        jsonObject.put("total", totalMoney);
        return jsonObject;
    }
}
