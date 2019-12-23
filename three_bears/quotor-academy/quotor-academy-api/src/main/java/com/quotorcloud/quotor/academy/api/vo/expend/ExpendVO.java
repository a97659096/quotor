package com.quotorcloud.quotor.academy.api.vo.expend;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExpendVO {

    /**
     * 唯一标识
     */
    @TableId(value = "e_id", type = IdType.UUID)
    private String id;

    private String eExpendTypeId;

    /**
     * 支出类别名称
     */
    private String eExpendTypeName;

    /**
     * 店铺标识
     */
    private String eShopId;

    /**
     * 店铺名称
     */
    private String eShopName;

    /**
     * 付款人
     */
    private String ePayment;

    /**
     * 支付方式:1现金，2微信，3支付宝，4pos机，5其他
     */
    private Integer ePayWay;

    /**
     * 金额
     */
    private BigDecimal eMoney;

    /**
     * 付款时间
     */
    private LocalDateTime ePayTime;

    /**
     * 支出内容
     */
    private String eExpendContent;

    /**
     * 关联员工
     */
    private List<String> eEmployeeNameList;

    /**
     * 相关图片
     */
    private List<JSONObject> eImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

}
