package com.quotorcloud.quotor.academy.controller.employee;


import com.quotorcloud.quotor.academy.service.employee.OrderDetailServiceStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单服务人员 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/order/employee")
public class OrderDetailServiceStaffController {

    @Autowired
    private OrderDetailServiceStaffService orderDetailServiceStaffService;

}
