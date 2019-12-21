package com.quotorcloud.quotor.academy.api.dto.appoint;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class AppointDTO {


    /**
     * 唯一标识
     */
    private String id;
    /**
     * 预约标识
     */
    private String appointId;
    /**
     * 预约人
     */
    private String memberName;

    /**
     * 预约人电话
     */
    private String memberPhone;

    /**
     * 人数
     */
    private Integer population;

    /**
     * 预约日期
     */
    private LocalDate appointDate;

    /**
     * 流水单号
     */
    private String wasteBook;

    /**
     * 备注
     */
    private String remark;

    /**
     * 店铺标识
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 预约状态
     */
    private Integer appointState;

    /**
     * 会员编号
     */
    private String memberNumber;

    /**
     * 会员头像
     */
    private String memberHeadImg;

    /**
     * 卡数
     */
    private Integer cardCounts;

    /**
     * 卡余
     */
    private BigDecimal cardBalance;

    /**
     * 最后一次消费信息
     */
    private String lastConsume;

    /**
     * 预约内容
     */
    private List<AppointContent> appointContents;

}
