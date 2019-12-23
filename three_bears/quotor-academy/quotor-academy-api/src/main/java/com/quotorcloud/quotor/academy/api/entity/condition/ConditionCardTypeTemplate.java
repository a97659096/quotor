package com.quotorcloud.quotor.academy.api.entity.condition;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 卡类型模板中间表
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_condition_card_type_template")
public class ConditionCardTypeTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型标识
     */
    @TableId(value = "type_id", type = IdType.UUID)
    private String typeId;

    /**
     * 模板标识
     */
    private String templateId;


}
