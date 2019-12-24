package com.quotorcloud.quotor.academy.api.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderIncomeDTO {

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
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

    /**
     * 指定日期用于按月份或者年查询
     */
    private String appointDate;

    private String startMoney;

    private String endMoney;

    private String sort;

    /**
     * 近多长时间，1  周，2 月，3 年
     */
    private Integer nearDate;


    private Integer pageNo;

    private Integer pageSize;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime eGmtCreate;

    /**
     * 1周，2月，3年
     */
    private Integer dateType = 1;
}
