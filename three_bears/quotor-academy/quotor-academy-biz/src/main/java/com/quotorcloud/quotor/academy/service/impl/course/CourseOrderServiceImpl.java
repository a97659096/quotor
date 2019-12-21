package com.quotorcloud.quotor.academy.service.impl.course;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.api.dto.course.CourseOrderDTO;
import com.quotorcloud.quotor.academy.api.entity.course.Course;
import com.quotorcloud.quotor.academy.api.entity.course.CourseOrder;
import com.quotorcloud.quotor.academy.api.vo.course.CourseAppointmentVO;
import com.quotorcloud.quotor.academy.config.WeChatConfig;
import com.quotorcloud.quotor.academy.mapper.course.CourseMapper;
import com.quotorcloud.quotor.academy.mapper.course.CourseOrderMapper;
import com.quotorcloud.quotor.academy.service.course.CourseOrderService;
import com.quotorcloud.quotor.admin.api.entity.SysRole;
import com.quotorcloud.quotor.admin.api.feign.RemoteUserService;
import com.quotorcloud.quotor.admin.api.vo.UserVO;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程订单表（课程预约） 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-05
 */
@Service
public class CourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder> implements CourseOrderService {

    @Autowired
    private CourseOrderMapper courseOrderMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 删除订单信息（预约列表）
     * @param id
     * @return
     */
    @Override
    public Boolean removeCourseOrder(String id) {
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setId(id);
        courseOrder.setDelState(CommonConstants.STATUS_DEL);
        courseOrderMapper.updateById(courseOrder);
        return Boolean.TRUE;
    }

    /**
     * 查询订单列表
     * @param courseOrderDTO
     * @return
     */
    @Override
    public JSONObject listCourseOrder(CourseOrderDTO courseOrderDTO) {

        //处理时间戳
        DateTimeUtil.setStartAndEndDate(CourseOrderDTO.class, courseOrderDTO);

        Page<CourseOrder> page = PageUtil.getPage(courseOrderDTO.getPageNo(), courseOrderDTO.getPageSize());

        IPage<CourseOrder> courseOrderIPage = courseOrderMapper.listCourseOrderPage(page, courseOrderDTO);

        return PageUtil.getPagePackage("courseOrders", courseOrderIPage.getRecords(), page);
    }

    /**
     * 查询预约列表
     * @param courseOrderDTO
     * @return
     */
    @Override
    public JSONObject listAppointment(CourseOrderDTO courseOrderDTO) {
        //处理时间戳
        DateTimeUtil.setStartAndEndDate(CourseOrderDTO.class, courseOrderDTO);

        Page<CourseOrder> page = PageUtil.getPage(courseOrderDTO.getPageNo(), courseOrderDTO.getPageSize());
        IPage<CourseAppointmentVO> iPage = courseOrderMapper.selectCourseAppointPage(page, courseOrderDTO);
        return PageUtil.getPagePackage("appointments", iPage.getRecords(), page);
    }


    /**
     * 微信native支付
     * @param request
     * @return
     */
    @Override
    public String saveCourseOrder(CourseOrderDTO courseOrderDTO, HttpServletRequest request) {
        CourseOrder courseOrder = getCourseOrder(courseOrderDTO, request, CommonConstants.NATIVE);
        //获取codeurl
        if(courseOrder != null){
            courseOrderMapper.insert(courseOrder);
            return unifiedOrder(courseOrder, CommonConstants.NATIVE);
        }
        return null;
    }

    /**
     * 微信jsapi支付
     * @param request 请求request
     * @return
     */
    @Override
    public Map<String, String> saveJSAPICourseOrder(CourseOrderDTO courseOrderDTO, HttpServletRequest request) {
        //获取openid
        String openIdUrl = String.format(WeChatConfig.getOpenIdUrl(), weChatConfig.getOpenAppid(), weChatConfig.getOpenAppsecret(), courseOrderDTO.getCode());
        Map<String, Object> map = HttpUtils.doGet(openIdUrl);

        if(ComUtil.isEmpty(map)){
            return null;
        }
        String openid = (String) map.get("openid");
        //查询出用户信息
        CourseOrder courseOrder = getCourseOrder(courseOrderDTO, request, CommonConstants.JSAPI);

        if(courseOrder != null) {
            courseOrder.setOpenId(openid);
            //插入订单
            courseOrderMapper.insert(courseOrder);
            //微信统一下单
            String prepayId = unifiedOrder(courseOrder, CommonConstants.JSAPI);
            //返回前端，调起支付
            SortedMap<String, String> params = new TreeMap<>();
            params.put("appId", weChatConfig.getAppId());
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));
            params.put("nonceStr", GenerationSequenceUtil.generateUUID(null));
            params.put("package", "prepay_id=" + prepayId);
            String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
            params.put("signType", sign);
            return params;
        }
        return null;
    }

    /**
     * 统一下单方法
     * @return
     */
    private String unifiedOrder(CourseOrder courseOrder, Integer type){

        //int i = 1/0;   //模拟异常
        //生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str", GenerationSequenceUtil.generateUUID(null));
        params.put("body",courseOrder.getCourseName());
        params.put("out_trade_no",courseOrder.getOutTradeNo());
        params.put("total_fee",courseOrder.getTotalFee().toString());
        params.put("spbill_create_ip",courseOrder.getIp());
        params.put("notify_url",weChatConfig.getAppointCoursePayCallbackUrl());
        //扫码支付
        if(type.equals(CommonConstants.NATIVE)){
            params.put("trade_type","NATIVE");
            params.put("product_id", courseOrder.getCourseId());
        }else if(type.equals(CommonConstants.JSAPI)){
            //小程序支付
            params.put("trade_type","JSAPI");
            params.put("openid", courseOrder.getOpenId());
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

    /**
     * 封装实体类
     * @param request   请求
     * @param payType 支付方式  native,jsapi
     * @return
     */
    private CourseOrder getCourseOrder(CourseOrderDTO courseOrderDTO, HttpServletRequest request, Integer payType) {
        //查询出用户信息
        R<UserVO> user = remoteUserService.user(courseOrderDTO.getUserId());
        UserVO data = user.getData();
        if(ComUtil.isEmpty(data)){
            return null;
        }
        String ip = IpUtils.getIpAddr(request);
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setUserId(courseOrderDTO.getUserId());
        courseOrder.setName(courseOrderDTO.getName());
        courseOrder.setPhone(courseOrderDTO.getPhone());
        courseOrder.setHeadImg(data.getAvatar());
        courseOrder.setNickname(data.getName());
        courseOrder.setIp(ip);
        courseOrder.setCourseId(courseOrderDTO.getCourseId());
        courseOrder.setPayState(CommonConstants.NOT_PAY);
        courseOrder.setDelState(CommonConstants.STATUS_NORMAL);
        courseOrder.setOrderState(CommonConstants.WAIT_PAY);
        courseOrder.setOutTradeNo(GenerationSequenceUtil.generateUUID(null));
        //微信扫码支付
        courseOrder.setPayType(payType);

        Course course = courseMapper.selectById(courseOrder.getCourseId());
        courseOrder.setCourseName(course.getName());
        List<String> roleList = data.getRoleList().stream().map(SysRole::getRoleCode).collect(Collectors.toList());
        //学员价格
        if(roleList.contains("student")){
            courseOrder.setTotalFee(course.getStudentPrice());
            //加盟商价格
        }else if(roleList.contains("shop")){
            courseOrder.setTotalFee(course.getJoinShopPrice());
        }else {
            throw new MyException(ExceptionEnum.NOT_FIND_ROLE);
        }
        return courseOrder;
    }
}
