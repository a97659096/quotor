package com.quotorcloud.quotor.academy.api.entity.banner;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * banner管理
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "b_id", type = IdType.UUID)
    private String id;

    /**
     * 图片
     */
    private String bImg;

    /**
     * 标题
     */
    private String bTitle;

    /**
     * 添加人标识
     */
    private String bAddUserId;

    /**
     * 添加人姓名
     */
    private String bAddUserName;

    /**
     * 店铺标识
     */
    private String bShopId;

    /**
     * 店铺名称
     */
    private String bShopName;

    /**
     * 状态，1启用，2禁用
     */
    private Integer bStatus;

    /**
     * 删除状态，0正常，1删除
     */
    private Integer bDelState;

    private String bLink;

    /**
     * 排序字段
     */
    private Integer eSort;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "b_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "b_gmt_modified")
    @JSONField(serialize = false)
    private LocalDateTime gmtModified;


}
