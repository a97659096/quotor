package com.quotorcloud.quotor.academy.api.entity.message;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信模板类别
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_message_template_category")
public class MessageTemplateCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "mc_category_id", type = IdType.UUID)
    private String categoryId;

    /**
     * 类别名称
     */
    @TableField(value = "mc_category_name")
    private String categoryName;

    @TableLogic
    @TableField(value = "mc_del_state")
    private Integer delState;

    /**
     * 创建时间
     */
    @TableField(value = "mc_gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "mc_gmt_modified")
    private LocalDateTime gmtModified;

}
