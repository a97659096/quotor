package com.quotorcloud.quotor.academy.api.entity.expend;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支出信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_expend")
public class Expend implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String eEmployeeNameList;

    /**
     * 相关图片
     */
    private String eImg;

    /**
     * 删除状态，0正常，1删除
     */
    private Integer eDelState;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "e_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "e_gmt_modified")
    private LocalDateTime gmtModified;


}
