package com.quotorcloud.quotor.academy.api.entity.condition;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 卡模板内容
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_card_template")
public class ConditionCardTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "cct_id", type = IdType.UUID)
    private String id;

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 属性标识
     */
    private String propertyId;

    /**
     * 状态：0正常，1删除
     */
    private Integer delState;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


}
