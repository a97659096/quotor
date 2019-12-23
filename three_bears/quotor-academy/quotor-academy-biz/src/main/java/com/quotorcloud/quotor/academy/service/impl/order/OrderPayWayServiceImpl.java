package com.quotorcloud.quotor.academy.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetail;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.quotorcloud.quotor.academy.api.entity.order.OrderPayWay;
import com.quotorcloud.quotor.academy.api.vo.order.OrderWebVO;
import com.quotorcloud.quotor.academy.mapper.order.OrderPayWayMapper;
import com.quotorcloud.quotor.academy.service.order.OrderPayWayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 支付方式表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-12
 */
@Service
public class OrderPayWayServiceImpl extends ServiceImpl<OrderPayWayMapper, OrderPayWay> implements OrderPayWayService {


}
