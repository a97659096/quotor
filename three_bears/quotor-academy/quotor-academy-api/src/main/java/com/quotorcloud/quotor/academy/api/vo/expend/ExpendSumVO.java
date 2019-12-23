package com.quotorcloud.quotor.academy.api.vo.expend;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpendSumVO {

    private String name;

    private BigDecimal value;

    @JSONField(serialize = false)
    private String color;

    private Object itemStyle;

}
