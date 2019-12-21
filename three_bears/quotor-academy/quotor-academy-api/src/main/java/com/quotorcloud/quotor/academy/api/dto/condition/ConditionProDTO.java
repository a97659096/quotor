package com.quotorcloud.quotor.academy.api.dto.condition;


import com.quotorcloud.quotor.academy.api.entity.condition.ConditionSetMealDetail;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ConditionProDTO {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 名称
     */
    private String pName;

    /**
     * 适用门店标识
     */
    private String shopId;

    /**
     * 适用门店名称
     */
    private String shopName;

    /**
     * 统一售价
     */
    private BigDecimal pUnifiedPrice;

    /**
     * 项目时长
     */
    private Integer pTimeLength;

    /**
     * 类别标识
     */
    private String pCategoryId;

    /**
     * 类别名称
     */
    private String pCategoryName;

    /**
     * 手机端是否可预约，1:可预约，2:不可预约
     */
    private Integer cSubscribe;

    /**
     * 编号
     */
    private String pNumber;

    /**
     * 项目体验价
     */
    private BigDecimal pExperiencePrice;

    /**
     * 描述
     */
    private String pDescribe;

    /**
     * 状态，1可用，2不可用
     */
    private Integer pState;

    /**
     * 项目配图
     */
    private MultipartFile[] pImg;

    private List<String> pImgString;
    /**
     * 删除状态，0正常，1删除
     */
    private Integer pDelState;

    /**
     * 产品类型
     */
    private Integer pProductType;

    /**
     * 产品单位
     */
    private String pProductUnit;

    /**
     * 产品品牌
     */
    private String pProductBrand;

    /**
     * 产品形态
     */
    private Integer pProductForm;

    /**
     * 产品容量
     */
    private String pProductCapacity;

    /**
     * 产品保质期
     */
    private String pProductExpirDate;

    private List<String> categoryIds;

    /**
     * 类型，1项目，2产品,3套餐
     */
    private Integer pType;

    private Integer pageNo;

    private Integer pageSize;

    private List<ConditionSetMealDetail> mealDetails;


}
