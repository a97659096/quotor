package com.quotorcloud.quotor.academy.service.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.order.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.order.OrderConsumeRecordVO;

import java.util.List;

/**
 * <p>
 * 订单信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface OrderService extends IService<Order> {

    /**
     * 开单 项目/产品
     * @param order
     * @return
     */
    Boolean saveOrderPro(Order order);

    /**
     * 开单 卡片
     * @param order
     * @return
     */
    Boolean saveOrderCard(Order order);

    /**
     * 按客户标识查询消费记录
     * @param memberId
     * @return
     */
    IPage listConsumeRecordByMemberId(Page page, OrderDTO orderDTO);



}
