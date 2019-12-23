package com.quotorcloud.quotor.academy.api.entity.inventory;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 库存信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bear_inventory")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "i_id", type = IdType.UUID)
    private String id;

    /**
     * 操作类型，1入库，2出库
     */
    @TableField(value = "i_type")
    private Integer type;

    /**
     * 店铺标识
     */
    @TableField(value = "i_shop_id")
    private String shopId;

    /**
     * 店铺名称
     */
    @TableField(value = "i_shop_name")
    private String shopName;

    /**
     * 产品标识
     */
    @TableField(value = "i_product_id")
    private String productId;

    /**
     * 产品名称
     */
    @TableField(exist = false)
    private String productName;

    /**
     * 产品编号
     */
    @TableField(exist = false)
    private String productNumber;

    /**
     * 产品数量
     */
    @TableField(value = "i_product_amount")
    private Integer productAmount;

    /**
     * 品牌名称
     */
    @TableField(exist = false)
    private String brandName;

    /**
     * 规格
     */
    @TableField(exist = false)
    private String standard;

    /**
     * 价格
     */
    @TableField(exist = false)
    private BigDecimal price;

    @TableField(exist = false)
    private String categoryName;

    /**
     * 经办人
     */
    @TableField(value = "i_operator")
    private String operator;

    /**
     * 用途
     */
    @TableField(value = "i_use")
    private String used;

    /**
     * 备注
     */
    @TableField(value = "i_remark")
    private String remark;

    /**
     * 操作日期
     */
    @TableField(value = "i_date")
    @JSONField(serialize = false)
    private LocalDate date;

    /**
     * 状态：0正常，1删除
     */
    @TableField(value = "i_state")
    @JSONField(serialize = false)
    private Integer state;

    /**
     * 创建时间
     */
    @TableField(value = "i_gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "i_gmt_modified", fill = FieldFill.INSERT_UPDATE)
    @JSONField(serialize = false)
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private Integer pageNo;

    @TableField(exist = false)
    private Integer pageSize;

    @TableField(exist = false)
    private Long createTime;

    @TableField(exist = false)
    private Integer surplus;

    @TableField(exist = false)
    private String dateRange;

    @TableField(exist = false)
    private String start;

    @TableField(exist = false)
    private String end;

    @TableField(exist = false)
    private List<String> categoryIds;

    @TableField(exist = false)
    private String categoryId;

    @TableField(exist = false)
    private Integer inventoryWarn;

    @TableField(exist = false)
    private BigDecimal unifiedPrice;

}
