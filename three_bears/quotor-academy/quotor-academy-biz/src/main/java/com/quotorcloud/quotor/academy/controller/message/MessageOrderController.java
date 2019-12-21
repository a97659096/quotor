package com.quotorcloud.quotor.academy.controller.message;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.message.MessageOrder;
import com.quotorcloud.quotor.academy.service.message.MessageOrderService;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 短信订单表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
@RestController
@RequestMapping("/message/order")
public class MessageOrderController {

    @Autowired
    private MessageOrderService messageOrderService;

    @Autowired
    private OrderUtil orderUtil;

    /**
     * 微信扫码付款
     * @param messageOrder
     * @param request
     * @param response
     */
    @PostMapping("native/pay")
    public void nativeOrderSave(MessageOrder messageOrder, HttpServletRequest request,
                                HttpServletResponse response){
        String codeurl = messageOrderService.saveMessageOrder(messageOrder, request);
        if(ComUtil.isEmpty(codeurl)){
            throw new NullPointerException();
        }
        orderUtil.genertorQRCode(codeurl, response);
    }

    /**
     * 查询短信订单列表
     * @param page
     * @param messageOrder
     * @return
     */
    @GetMapping("list")
    public R listOrder(Page page, MessageOrder messageOrder){
        return R.ok(messageOrderService.listMessageOrder(page, messageOrder));
    }


}
