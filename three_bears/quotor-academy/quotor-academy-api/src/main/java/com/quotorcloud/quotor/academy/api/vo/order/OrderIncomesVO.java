package com.quotorcloud.quotor.academy.api.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderIncomesVO {

    /**
     * 内容名称
     */
    private String contentName;
    /**
     * 金额
     */
    private BigDecimal orderMoney;
    /**
     * 员工名字
     */
    private String employeeName;
    /**
     * 开始日期
     */
    private String start;

    /**
     * 结束日期
     */
    private String end;
    /**
     * 创建时间
     */
    private LocalDateTime gmtModified;
    /**
     * 名字查询
     */
    private String searchName;
    /**
     * 会员名
     */
    private String memberName;
    /**
     * 会员头像
     */
    private String memberHeadImg;
}
