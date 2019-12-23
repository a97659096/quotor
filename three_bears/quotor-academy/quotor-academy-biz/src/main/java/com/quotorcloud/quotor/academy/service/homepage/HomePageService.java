package com.quotorcloud.quotor.academy.service.homepage;

import com.alibaba.fastjson.JSONObject;

public interface HomePageService {

    /**
     * 查询加盟商首页上方到店客人、新增会员、预约人数
     * @param shopId
     * @return
     */
    JSONObject onceGuest(String shopId);
}
