package com.quotorcloud.quotor.academy.service.impl.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.message.MessageOrder;
import com.quotorcloud.quotor.academy.config.WeChatConfig;
import com.quotorcloud.quotor.academy.mapper.message.MessageOrderMapper;
import com.quotorcloud.quotor.academy.service.message.MessageOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.GenerationSequenceUtil;
import com.quotorcloud.quotor.common.core.util.HttpUtils;
import com.quotorcloud.quotor.common.core.util.IpUtils;
import com.quotorcloud.quotor.common.core.util.WXPayUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 * 短信订单表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
@Service
public class MessageOrderServiceImpl extends ServiceImpl<MessageOrderMapper, MessageOrder> implements MessageOrderService {

    @Autowired
    private MessageOrderMapper messageOrderMapper;

    @Autowired
    private OrderUtil orderUtil;

    @Autowired
    private WeChatConfig weChatConfig;


    @Override
    public String saveMessageOrder(MessageOrder messageOrder, HttpServletRequest request) {
        //获取当前用户
        QuotorUser user = SecurityUtils.getUser();
        messageOrder.setIp(IpUtils.getIpAddr(request));
        messageOrder.setPayState(CommonConstants.NOT_PAY);
        messageOrder.setOutTradeNo(orderUtil.generateOrderSn());
        messageOrder.setPurchaser(user.getName());
        messageOrder.setPurchaserId(String.valueOf(user.getId()));
        messageOrder.setShopId(String.valueOf(user.getDeptId()));
        messageOrder.setShopName(user.getDeptName());
        messageOrder.setPayState(CommonConstants.NATIVE);
        //扫码支付
        return unifiedOrder(messageOrder, CommonConstants.NATIVE);
    }

    @Override
    public IPage listMessageOrder(Page page, MessageOrder messageOrder) {
        return messageOrderMapper.selectPage(page, new QueryWrapper<>());
    }

    /**
     * 统一下单方法
     * @return
     */
    private String unifiedOrder(MessageOrder messageOrder, Integer type){

        //int i = 1/0;   //模拟异常
        //生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str", GenerationSequenceUtil.generateUUID(null));
        params.put("body",messageOrder.getTitle());
        params.put("out_trade_no",messageOrder.getOutTradeNo());
        params.put("total_fee",messageOrder.getPrice().toString());
        params.put("spbill_create_ip",messageOrder.getIp());
        params.put("notify_url",weChatConfig.getAppointCoursePayCallbackUrl());
        //扫码支付
        if(type.equals(CommonConstants.NATIVE)){
            params.put("trade_type","NATIVE");
            params.put("product_id", messageOrder.getId());
        }else if(type.equals(CommonConstants.JSAPI)){
            //小程序支付
            params.put("trade_type","JSAPI");
            params.put("openid", messageOrder.getOpenId());
        }

        //sign签名
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);

        //map转xml
        String payXml = null;
        try {
            payXml = WXPayUtil.mapToXml(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(payXml);
        //统一下单
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(),payXml,4000);
        if(null == orderStr) {
            return null;
        }

        Map<String, String> unifiedOrderMap = null;
        try {
            unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(unifiedOrderMap.toString());
        if(type.equals(CommonConstants.NATIVE)){
            return unifiedOrderMap.get("code_url");
        }else if(type.equals(CommonConstants.JSAPI)){
            return unifiedOrderMap.get("prepay_id");
        }else {
            return null;
        }

    }
}
