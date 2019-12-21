package com.quotorcloud.quotor.academy.service.message;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.message.MessageOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 短信订单表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
public interface MessageOrderService extends IService<MessageOrder> {

    String saveMessageOrder(MessageOrder messageOrder, HttpServletRequest request);

    IPage listMessageOrder(Page page, MessageOrder messageOrder);

}
