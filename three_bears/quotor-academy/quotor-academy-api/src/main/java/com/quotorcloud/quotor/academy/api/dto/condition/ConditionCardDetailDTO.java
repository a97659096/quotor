package com.quotorcloud.quotor.academy.api.dto.condition;

import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ConditionCardDetailDTO implements Comparable<ConditionCardDetailDTO> {

    /**
     * 充值满赠金额
     */
    private BigDecimal key;

    /**
     * 赠送内容
     */
    private List<ConditionCardDetail> value;

    @Override
    public int compareTo(ConditionCardDetailDTO o) {
        return this.key.compareTo(o.getKey());
    }
}
