package com.quotorcloud.quotor.admin.api.vo;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DeptVO {

    private Integer deptId;

    /**
     * 店铺头像
     */
    private String headImg;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺编号
     */
    private String number;

    /**
     *店铺简介
     */
    private String intro;

    private String environmentDatabase;

    /**
     * 店铺环境
     */
    private List<JSONObject> environment;

    /**
     * 店铺负责人
     */
    private String manager;

    /**
     * 店铺联系电话
     */
    private String phone;

    private Integer messageCount;
}
