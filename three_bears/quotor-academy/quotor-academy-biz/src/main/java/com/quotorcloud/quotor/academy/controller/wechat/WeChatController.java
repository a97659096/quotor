package com.quotorcloud.quotor.academy.controller.wechat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotorcloud.quotor.academy.api.entity.course.CourseOrder;
import com.quotorcloud.quotor.academy.api.entity.message.MessageOrder;
import com.quotorcloud.quotor.academy.config.WeChatConfig;
import com.quotorcloud.quotor.academy.service.course.CourseOrderService;
import com.quotorcloud.quotor.academy.service.message.MessageOrderService;
import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.admin.api.vo.DeptVO;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.core.util.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.SortedMap;

@RestController
@RequestMapping("wechat")
public class WeChatController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private MessageOrderService messageOrderService;

    @Autowired
    private RemoteDeptService remoteDeptService;

    /**
     * 课程订单微信支付回调
     */
    @RequestMapping("/course/order/callback")
    public void CourseOrderCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream =  request.getInputStream();

        //BufferedReader是包装设计模式，性能更搞
        BufferedReader in =  new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line ;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap.toString());

        SortedMap<String,String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

        //判断签名是否正确
        if(WXPayUtil.isCorrectSign(sortedMap,weChatConfig.getKey())){

            if("SUCCESS".equals(sortedMap.get("result_code"))){

                String outTradeNo = sortedMap.get("out_trade_no");
                CourseOrder dbCourseOrder = courseOrderService.getOne(new QueryWrapper<CourseOrder>().eq("out_trade_no",
                        outTradeNo));

                if(dbCourseOrder != null && dbCourseOrder.getPayState()==2){  //判断逻辑看业务场景
                    CourseOrder courseOrder = new CourseOrder();
                    courseOrder.setOpenId(sortedMap.get("openid"));
                    courseOrder.setOutTradeNo(outTradeNo);
                    courseOrder.setNotifyTime(LocalDateTime.now());
                    courseOrder.setPayState(CommonConstants.PAY);
                    boolean rows = courseOrderService.update(courseOrder, new QueryWrapper<CourseOrder>()
                            .eq("out_trade_no", courseOrder.getOutTradeNo()));
                    if(rows){ //通知微信订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().println("success");
                        return;
                    }
                }
            }
        }
        //都处理失败
        response.setContentType("text/xml");
        response.getWriter().println("fail");
    }

    /**
     * 短信订单微信支付回调
     */
    @RequestMapping("/message/order/callback")
    public void orderCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        InputStream inputStream =  request.getInputStream();

        //BufferedReader是包装设计模式，性能更搞
        BufferedReader in =  new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line ;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap.toString());

        SortedMap<String,String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

        //判断签名是否正确
        if(WXPayUtil.isCorrectSign(sortedMap,weChatConfig.getKey())){

            if("SUCCESS".equals(sortedMap.get("result_code"))){

                String outTradeNo = sortedMap.get("out_trade_no");
                MessageOrder messageOrder = messageOrderService.getOne(new QueryWrapper<MessageOrder>().eq("out_trade_no",
                        outTradeNo));

                if(messageOrder != null && messageOrder.getPayState()==2){  //判断逻辑看业务场景
                    MessageOrder order = new MessageOrder();
                    order.setOpenId(sortedMap.get("openid"));
                    order.setOutTradeNo(outTradeNo);
                    order.setPayTime(LocalDateTime.now());
                    order.setPayState(CommonConstants.PAY);
                    boolean rows = messageOrderService.update(order, new QueryWrapper<MessageOrder>()
                            .eq("out_trade_no", order.getOutTradeNo()));
                    //查询部门短信余量
                    R result = remoteDeptService.listDeptById(Integer.valueOf(messageOrder.getShopId()));
                    DeptVO deptVO = (DeptVO) result.getData();
                    DeptDTO deptDTO = new DeptDTO();
                    deptDTO.setDeptId(Integer.valueOf(messageOrder.getShopId()));
                    //增加短信数量
                    deptDTO.setMessageCount(deptVO.getMessageCount() + messageOrder.getAmount());
                    //修改短信余量
                    remoteDeptService.updateDept(deptDTO);
                    if(rows){ //通知微信订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().println("success");
                        return;
                    }
                }
            }
        }
        //都处理失败
        response.setContentType("text/xml");
        response.getWriter().println("fail");
    }
}
