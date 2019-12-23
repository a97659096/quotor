package com.quotorcloud.quotor.academy.api.vo.member;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberLogVO {

    private String id;

    /**
     * 员工标识
     */
    private String employeeId;

    private String employeeName;

    private String employeeHeadImg;


    /**
     * 客户标识
     */
    private String memberId;

    private String memberName;

    private String memberHeadImg;

    /**
     * 备注
     */
    private String remark;

    /**
     * 相关图片
     */
    private String imgDatabase;

    private List<JSONObject> img;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
