package com.quotorcloud.quotor.academy.controller.homepage;


import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.api.dto.expend.ExpendDTO;
import com.quotorcloud.quotor.academy.api.dto.member.MemberDTO;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCard;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.quotorcloud.quotor.academy.service.expend.ExpendService;
import com.quotorcloud.quotor.academy.service.homepage.HomePageService;
import com.quotorcloud.quotor.academy.service.member.MemberCardService;
import com.quotorcloud.quotor.academy.service.member.MemberService;
import com.quotorcloud.quotor.academy.service.order.OrderDetailPayService;
import com.quotorcloud.quotor.academy.service.order.OrderService;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("home/page")
public class HomePageController {

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private OrderDetailPayService orderDetailPayService;

    @Autowired
    private ExpendService expendService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private HomePageService homePageService;

    /**
     * 小程序首页
     * @param shopId
     * @return
     */
    @GetMapping("applet/total")
    public R listAppletTotal(String shopId){
        //计算总余量
        MemberCard memberCard = new MemberCard();
        memberCard.setShopId(shopId);
        JSONObject totalCardMargin = memberCardService.countTotalCardMargin(memberCard);

        //计算总卡耗
        OrderDetailPay orderDetailPay = new OrderDetailPay();
        orderDetailPay.setShopId(shopId);
        JSONObject totalCardConsume = orderDetailPayService.countTotalCardConsume(orderDetailPay);

        //计算总支出
        ExpendDTO expendDTO = new ExpendDTO();
        expendDTO.setEShopId(shopId);
        JSONObject totalExpend = expendService.countTotalExpend(expendDTO);

        //计算总会员数
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setShopId(shopId);
        JSONObject memberTimes = memberService.countMemberTimes(memberDTO);

        //计算今日收入
        OrderDetailPay detailPay = new OrderDetailPay();
        detailPay.setShopId(shopId);
        detailPay.setStart(DateTimeUtil.localDateToString(LocalDate.now()));
        JSONObject totalIncome = orderDetailPayService.countDailyTotalIncome(detailPay);

        JSONObject guest = homePageService.onceGuest(shopId);

        JSONObject jsonObject = new JSONObject();

        for (String key:totalCardMargin.keySet()){
            jsonObject.put(key, totalCardMargin.get(key));
        }

        for (String key:totalCardConsume.keySet()){
            jsonObject.put(key, totalCardConsume.get(key));
        }

        for (String key:totalExpend.keySet()){
            jsonObject.put(key, totalExpend.get(key));
        }

        for (String key:memberTimes.keySet()){
            jsonObject.put(key, memberTimes.get(key));
        }

        for (String key:totalIncome.keySet()){
            jsonObject.put(key, totalIncome.get(key));
        }

        for (String key:guest.keySet()){
            jsonObject.put(key, guest.get(key));
        }

        return R.ok(jsonObject);
    }

    /**
     * 查询加盟商首页上方到店客人、新增会员、预约人数
     * @param shopId
     * @return
     */
    @GetMapping("applet/guest")
    public R listOnceTheGuest(String shopId){
        return R.ok(homePageService.onceGuest(shopId));
    }

    /**
     * 查询总余量
     * @param shopId
     * @return
     */
    @GetMapping("applet/total/margin")
    public R listAppletTotalMargin(String shopId){
        MemberCard memberCard = new MemberCard();
        memberCard.setShopId(shopId);
        return R.ok(memberCardService.countTotalCardMarginGroupByShopId(memberCard));
    }

    /**
     * 查询店铺下面的所有会员的卡余量信息
     * @param memberCard
     * @return
     */
    @GetMapping("applet/total/margin/shop")
    public R listAppletTotalMargin(MemberCard memberCard){
        return R.ok(memberCardService.countTotalCardMarginGroupByShopId(memberCard));
    }

    /**
     * 计算总卡耗
     * @param shopId
     * @return
     */
    @GetMapping("applet/total/consume")
    public R listAppletTotalConsume(String shopId){
        OrderDetailPay orderDetailPay = new OrderDetailPay();
        orderDetailPay.setShopId(shopId);
        return R.ok(orderDetailPayService.countTotalCardConsumeGroupByShopId(orderDetailPay));
    }

    /**
     * 查询会员总个数
     * @param shopId
     * @return
     */
    @GetMapping("applet/total/member-count")
    public R listAppletTotalMemberCount(String shopId){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setShopId(shopId);
        return R.ok(memberService.countMemberTimesGroupByShopId(memberDTO));
    }

}
