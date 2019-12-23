package com.quotorcloud.quotor.academy.service.impl.inn;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacher;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacherOrder;
import com.quotorcloud.quotor.academy.api.vo.inn.InnTeacherAppointVO;
import com.quotorcloud.quotor.academy.config.WeChatConfig;
import com.quotorcloud.quotor.academy.mapper.inn.InnTeacherMapper;
import com.quotorcloud.quotor.academy.mapper.inn.InnTeacherOrderMapper;
import com.quotorcloud.quotor.academy.service.inn.InnTeacherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.*;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <p>
 * 下店订单表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@Service
public class InnTeacherOrderServiceImpl extends ServiceImpl<InnTeacherOrderMapper, InnTeacherOrder> implements InnTeacherOrderService {

    @Autowired
    private InnTeacherMapper innTeacherMapper;

    @Autowired
    private OrderUtil orderUtil;

    @Autowired
    private InnTeacherOrderMapper innTeacherOrderMapper;

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 微信扫码支付
     * @param innTeacherOrder
     * @param request
     * @return
     */
    @Override
    public String saveInnTeacherOrderNATIVE(InnTeacherOrder innTeacherOrder, HttpServletRequest request) {
        mapTeacherOrder(innTeacherOrder, request);
        this.save(innTeacherOrder);
        return unifiedOrder(innTeacherOrder, CommonConstants.NATIVE);
    }

    /**
     * 小程序支付
     * @param innTeacherOrder
     * @param request
     * @return
     */
    @Override
    public String saveInnTeacherOrderJSAPI(InnTeacherOrder innTeacherOrder, HttpServletRequest request) {
        return null;
    }

    @Override
    public IPage<InnTeacherAppointVO> listTeacherAppoint(Page<InnTeacherAppointVO> page, InnTeacherOrder innTeacherOrder) {
        //如果不为空，则需要按时间范围进行查询，进行转换并赋值
        if(!ComUtil.isEmpty(innTeacherOrder.getDateRange())){
            List<String> stringDate = DateTimeUtil.getStringDate(innTeacherOrder.getDateRange());
            innTeacherOrder.setStart(stringDate.get(0));
            innTeacherOrder.setEnd(stringDate.get(1));
        }
        return innTeacherOrderMapper
                .listAppointTeacherPage(page, innTeacherOrder);
    }

    private void mapTeacherOrder(InnTeacherOrder innTeacherOrder, HttpServletRequest request) {
        //封装预约讲师订单信息
        QuotorUser user = SecurityUtils.getUser();
        String ip = IpUtils.getIpAddr(request);
        //处理时间范围
        List<String> stringDate = DateTimeUtil.getStringDate(innTeacherOrder.getDateRange());
        if(!ComUtil.isEmpty(stringDate)){
            //赋值
            innTeacherOrder.setStartDate(DateTimeUtil.stringToLocalDate(stringDate.get(0)));
            innTeacherOrder.setEndDate(DateTimeUtil.stringToLocalDate(stringDate.get(1)));
        }
        innTeacherOrder.setDelState(CommonConstants.STATUS_NORMAL);
        innTeacherOrder.setPayState(CommonConstants.NOT_PAY);
        innTeacherOrder.setIp(ip);
        innTeacherOrder.setOrderState(CommonConstants.WAIT_PAY);
        innTeacherOrder.setShopId(String.valueOf(user.getDeptId()));
        innTeacherOrder.setOutTradeNo(orderUtil.generateOrderSn());
        innTeacherOrder.setPayType(CommonConstants.NATIVE);
        InnTeacher innTeacher = innTeacherMapper.selectById(innTeacherOrder.getInnTeacherId());
        innTeacherOrder.setInnTeacherName(innTeacher.getTeacherName());
    }

    /**
     * 统一下单方法
     * @return
     */
    private String unifiedOrder(InnTeacherOrder innTeacherOrder, Integer type){

        //int i = 1/0;   //模拟异常
        //生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str", GenerationSequenceUtil.generateUUID(null));
        params.put("body", "下店老师:" + innTeacherOrder.getInnTeacherName());
        params.put("out_trade_no",innTeacherOrder.getOutTradeNo());
        params.put("total_fee",innTeacherOrder.getTotalFee().multiply(BigDecimal.valueOf(100)).toString());
        params.put("spbill_create_ip",innTeacherOrder.getIp());
        params.put("notify_url",weChatConfig.getAppointTeacherPayCallbackUrl());
        //扫码支付
        if(type.equals(CommonConstants.NATIVE)){
            params.put("trade_type","NATIVE");
            params.put("product_id", innTeacherOrder.getInnTeacherId());
        }else if(type.equals(CommonConstants.JSAPI)){
            //小程序支付
            params.put("trade_type","JSAPI");
            params.put("openid", innTeacherOrder.getOpenId());
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
