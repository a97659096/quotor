package com.quotorcloud.quotor.common.core.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageUtil {

    //封装分页插件
    public static <T> Page<T> getPage(Integer pageNo, Integer pageSize){
        return new Page<>(!ComUtil.isEmpty(pageNo) ? pageNo : 1
                , !ComUtil.isEmpty(pageSize) ? pageSize : 20);
    }

    public static <T> JSONObject getPagePackage(String key, T t, Page page){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, t);
        jsonObject.put("total", page.getTotal());
        jsonObject.put("pageSize", page.getSize());
        jsonObject.put("currentPage", page.getCurrent());
        return jsonObject;
    }
}
