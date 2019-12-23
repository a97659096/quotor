package com.quotorcloud.quotor.academy.service.impl.homepage;


import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.mapper.appoint.AppointMapper;
import com.quotorcloud.quotor.academy.mapper.member.MemberMapper;
import com.quotorcloud.quotor.academy.mapper.order.OrderMapper;
import com.quotorcloud.quotor.academy.service.homepage.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AppointMapper appointMapper;

    @Override
    public JSONObject onceGuest(String shopId) {
        JSONObject jsonObject = new JSONObject();
        Integer storeGuest = orderMapper.selectToStoreGuest(shopId);
        Integer appointCounts = appointMapper.selectAppointCounts(shopId);
        Integer newMemberCounts = memberMapper.selectNewMemberCounts(shopId);
        jsonObject.put("storeGuest", storeGuest);
        jsonObject.put("appointCounts", appointCounts);
        jsonObject.put("newMemberCounts", newMemberCounts);
        return jsonObject;
    }
}
