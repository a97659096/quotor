package com.quotorcloud.quotor.academy.api.vo.employee;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeScheduClassVO {

    private String id;

    /**
     * 班次标识颜色
     */
    private String color;

    /**
     * 班次名称
     */
    private String name;

    /**
     * 适用门店（门店标识）
     */
    private String shopId;

    /**
     * 创建人标识
     */
    private String createrId;

    /**
     * 创建人名称
     */
    private String createrName;

    /**
     * 工作时段
     */
    private List<String> workTimeScope;


}
