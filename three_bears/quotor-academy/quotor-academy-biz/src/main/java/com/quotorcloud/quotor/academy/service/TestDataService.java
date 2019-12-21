package com.quotorcloud.quotor.academy.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface TestDataService {

    JSONObject selectProductById(String id);

    JSONObject selectShopById(String id);

    List<JSONObject> listProduct();

    List<JSONObject> listShop();
}
