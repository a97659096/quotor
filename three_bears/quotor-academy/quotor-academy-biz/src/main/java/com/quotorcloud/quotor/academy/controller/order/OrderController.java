package com.quotorcloud.quotor.academy.controller.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.order.Order;
import com.quotorcloud.quotor.academy.service.order.OrderService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 开单购卡
     * @param order
     * @return
     */
    @PostMapping("purchase-card")
    public R purchaseCard(@RequestBody Order order){
        return R.ok(orderService.saveOrderCard(order));
    }

    /**
     * 开单项目或者产品
     * @param order
     * @return
     */
    @PostMapping("purchase-pro")
    public R purchasePro(@RequestBody Order order){
        return R.ok(orderService.saveOrderPro(order));
    }

    /**
     * 按会员id查询消费记录
     * @param memberId
     * @return
     */
    @GetMapping("list/consume/record")
    public R listConsumeRecordByMemberId(Page page, OrderDTO orderDTO){
        return R.ok(orderService.listConsumeRecordByMemberId(page, orderDTO));
    }


}
