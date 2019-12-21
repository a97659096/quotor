package com.quotorcloud.quotor.academy.api.dto.expend;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExpendDTO {

    /**
     * 唯一标识
     */
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

    private String shopId;

    private String shopName;

    /**
     * 店铺名称
     */
    private String eShopName;

    /**
     * 付款人
     */
    private String ePayment;

    /**
     * 支付方式名称
     */
    private Integer ePayWay;

    /**
     * 金额
     */
    private BigDecimal eMoney;

//    /**
//     * 付款时间
//     */
//    private LocalDateTime ePayTime;

    /**
     * 支出内容
     */
    private String eExpendContent;

    /**
     * 关联员工
     */
    private List<String> eEmployeeNameList;

    private String dateRange;

    private String start;

    private String end;

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

    /**
     * 相关图片
     */
    private MultipartFile[] eImg;

    private Integer pageNo;

    private Integer pageSize;

    private List<String> eImgString;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime eGmtCreate;

}
