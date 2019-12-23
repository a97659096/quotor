package com.quotorcloud.quotor.academy.api.vo.invitejob;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class InviteJobVO {

    private String id;

    /**
     * 岗位标识
     */
    private String positionId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 福利
     */
    private String welfare;

    /**
     * 工资（日新） 始值
     */
    private BigDecimal salaryStart;

    /**
     * 工资 终值
     */
    private BigDecimal salaryEnd;

    /**
     * 地址
     */
    private String address;

    /**
     * 所属店铺标识
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺头像
     */
    private String shopHeadImg;

    /**
     * 招聘状态，1发布，2下架，3待发布
     */
    private Integer status;

    /**
     * 岗位详情
     */
    private String positionDetail;

    /**
     * 技能要求
     */
    private String skillRequire;

    /**
     * 技能特长
     */
    private String skillSuper;

    /**
     * 个人详情
     */
    private String ownerDetail;

    private LocalDate startDate;

    private LocalDate endDate;

    /**
     * 开始结束日期
     */
    private List<Long> rangeDate;

    /**
     * 发布时间
     */
    private LocalDateTime issueTime;

    /**
     * 类型，1招聘，2下店
     */
    private Integer type;

    /**
     * 下店人员标识
     */
    private String xdrId;

    /**
     * 下店人员名称
     */
    private String xdrName;

    private String xdrHeadImg;

    private String imgDatabase;

    private List<JSONObject> img;

}
