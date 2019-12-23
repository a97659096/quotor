package com.quotorcloud.quotor.academy.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.service.TestDataService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestDataServiceImpl implements TestDataService {

    /**
     * 测试数据 产品
     * @param id
     * @return
     */
    @Override
    public JSONObject selectProductById(String id) {
        Map<String, JSONObject> map = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productName", "面膜"+i);
            jsonObject.put("productNumber", "00" + i);
            jsonObject.put("brandName", "品牌"+i);
            jsonObject.put("standard", "规格"+i);
            jsonObject.put("price", "100"+i);
            jsonObject.put("id", "000"+i);
            jsonObject.put("shopId", "02"+i);
            jsonObject.put("shopName", "三只熊"+i+"号店");
            map.put("000"+i, jsonObject);
        }
        return map.get(id);
    }

    /**
     * 测试数据  店铺
     * @param id
     * @return
     */
    @Override
    public JSONObject selectShopById(String id) {
        Map<String, JSONObject> map = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shopName", "三只熊"+i+"号店");
            jsonObject.put("id", "02"+i);
            map.put("02"+i, jsonObject);
        }
        return map.get(id);
    }

    @Override
    public List<JSONObject> listProduct() {
        List<JSONObject> jsonObjects = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productName", "面膜"+i);
            jsonObject.put("productNumber", "00" + i);
            jsonObject.put("brandName", "品牌"+i);
            jsonObject.put("standard", "规格"+i);
            jsonObject.put("price", "100"+i);
            jsonObject.put("id", "000"+i);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    @Override
    public List<JSONObject> listShop() {
        List<JSONObject> list = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shopName", "三只熊"+i+"号店");
            jsonObject.put("id", "000"+i);
            list.add(jsonObject);
        }
        return list;
    }
}
