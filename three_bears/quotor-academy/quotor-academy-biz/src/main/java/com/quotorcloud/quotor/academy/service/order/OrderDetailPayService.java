package com.quotorcloud.quotor.academy.service.order;

import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单付款详情 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface OrderDetailPayService extends IService<OrderDetailPay> {

    /**
     * 计算总卡耗
     * @param orderDTO
     * @return
     */
    JSONObject countTotalCardConsume(OrderDetailPay detailPay);

    /**
     * 计算总收入并按支付方式分类
     */
    JSONObject countDailyTotalIncome(OrderDetailPay detailPay);

    /**
     * 查询总卡耗并按门店分组
     * @return
     */
    JSONObject countTotalCardConsumeGroupByShopId(OrderDetailPay detailPay);
}
