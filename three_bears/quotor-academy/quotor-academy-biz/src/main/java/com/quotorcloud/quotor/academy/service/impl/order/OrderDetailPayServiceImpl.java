package com.quotorcloud.quotor.academy.service.impl.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.quotorcloud.quotor.academy.api.entity.order.OrderPayWay;
import com.quotorcloud.quotor.academy.api.vo.order.OrderIncomeVO;
import com.quotorcloud.quotor.academy.api.vo.order.OrderWebVO;
import com.quotorcloud.quotor.academy.mapper.order.OrderDetailPayMapper;
import com.quotorcloud.quotor.academy.mapper.order.OrderPayWayMapper;
import com.quotorcloud.quotor.academy.service.order.OrderDetailPayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.admin.api.entity.SysDept;
import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单付款详情 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Service
public class OrderDetailPayServiceImpl extends ServiceImpl<OrderDetailPayMapper, OrderDetailPay> implements OrderDetailPayService {

    @Autowired
    private OrderDetailPayMapper orderDetailPayMapper;

    @Autowired
    private OrderPayWayMapper orderPayWayMapper;

    @Autowired
    private RemoteDeptService remoteDeptService;

    /**
     * 计算总卡耗
     * @param orderDTO
     * @return
     */
    @Override
    public JSONObject countTotalCardConsume(OrderDetailPay detailPay) {
        List<OrderDetailPay> orderDetailPays = orderDetailPayMapper.listOrderDetailPay(detailPay);
        BigDecimal cardConsumeMoney = new BigDecimal(0);
        int cardConsumeTimes = 0;
        for (OrderDetailPay orderDetailPay : orderDetailPays){
            //卡耗
            if(orderDetailPay.getPayType().equals(1)){
                //金额
                if(orderDetailPay.getPayMoneyType().equals(1)){
                    cardConsumeMoney = cardConsumeMoney.add(orderDetailPay.getPayMoney());
                    //次数
                }else {
                    cardConsumeTimes = cardConsumeTimes + Integer.valueOf(String.valueOf(orderDetailPay.getPayMoney()));
                }
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cardConsumeMoney", cardConsumeMoney);
        jsonObject.put("cardConsumeTimes", cardConsumeTimes);
        return jsonObject;
    }

    /**
     * 计算总收入并分组
     * @param orderDTO
     * @return
     */
    @Override
    public JSONObject countDailyTotalIncome(OrderDetailPay detailPay) {
        //查询根据业绩类型分类收入
        List<OrderIncomeVO> orderIncomeVOS = orderDetailPayMapper
                .listOrderIncomeVO(detailPay);

        List<String> x = new ArrayList<>();
        List<BigDecimal> y = new ArrayList<>();
        for (OrderIncomeVO orderIncomeVO:orderIncomeVOS){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("color", orderIncomeVO.getColor());
            orderIncomeVO.setItemStyle(jsonObject);
            x.add(orderIncomeVO.getPayWayName());
            y.add(orderIncomeVO.getMoney());
        }

        //计算总收入
        BigDecimal totalIncome = orderIncomeVOS.stream().map(OrderIncomeVO::getMoney)
                .reduce(BigDecimal::add).get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalIncome", totalIncome);
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("details", orderIncomeVOS);
        return jsonObject;
    }

    /**
     * 查询总卡耗并按门店分组
     * @return
     */
    @Override
    public JSONObject countTotalCardConsumeGroupByShopId(OrderDetailPay detailPay) {
        List<OrderDetailPay> orderDetailPays = orderDetailPayMapper.listOrderDetailPay(detailPay);
        Map<String, List<OrderDetailPay>> orderDetailPayByShopIdMap = orderDetailPays.stream()
                .collect(Collectors.groupingBy(OrderDetailPay::getShopId));

        R r = remoteDeptService.listDeptAll();
        List<SysDept> sysDepts = JSON.parseArray(JSON.toJSONString(r.getData()), SysDept.class);
        Map<Integer, SysDept> deptById = Maps.uniqueIndex(sysDepts, SysDept::getDeptId);

        BigDecimal totalCardConsumeMoney = new BigDecimal(0);
        Integer totalCardConsumeTimes = 0;
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (String shopId: orderDetailPayByShopIdMap.keySet()){
            BigDecimal cardConsumeMoney = new BigDecimal(0);
            int cardConsumeTimes = 0;
            for (OrderDetailPay orderDetailPay : orderDetailPays){
                //卡耗
                if(orderDetailPay.getPayType().equals(1)){
                    //金额
                    if(orderDetailPay.getPayMoneyType().equals(1)){
                        cardConsumeMoney = cardConsumeMoney.add(orderDetailPay.getPayMoney());
                        //次数
                    }else {
                        cardConsumeTimes = cardConsumeTimes + Integer.valueOf(String.valueOf(orderDetailPay.getPayMoney()));
                    }
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cardConsumeMoney", cardConsumeMoney);
            jsonObject.put("cardConsumeTimes", cardConsumeTimes);
            jsonObject.put("shopId", shopId);
            jsonObject.put("shopName", deptById.get(Integer.valueOf(shopId)).getName());
            jsonObject.put("shopHeadImg", deptById.get(Integer.valueOf(shopId)).getHeadImg());
            totalCardConsumeMoney = totalCardConsumeMoney.add(cardConsumeMoney);
            totalCardConsumeTimes = totalCardConsumeTimes + cardConsumeTimes;
            jsonObjects.add(jsonObject);
        }
        result.put("totalConsumeMoney", totalCardConsumeMoney);
        result.put("totalConsumeTimes", totalCardConsumeTimes);
        result.put("list", jsonObjects);
        return result;
    }
}
