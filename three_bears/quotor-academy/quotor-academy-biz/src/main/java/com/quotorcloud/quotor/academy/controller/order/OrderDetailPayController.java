package com.quotorcloud.quotor.academy.controller.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.message.MessageRemind;
import com.quotorcloud.quotor.academy.api.vo.order.OrderIncomesVO;
import com.quotorcloud.quotor.academy.service.order.OrderDetailPayService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单付款详情 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/order-detail-pay/")
public class OrderDetailPayController {
    @Autowired
    private OrderDetailPayService orderDetailPayService;

    /**
     * 查询短信提醒列表
     * @param orderIncomesVO
     * @return
     */
    @GetMapping("list")
    public R countOrderIncome(OrderIncomesVO orderIncomesVO){
        return R.ok(orderDetailPayService.countOrderIncome(orderIncomesVO));
    }

}
