package com.quotorcloud.quotor.academy.api.vo.expend;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpendSumVO {

    private String date;

    private BigDecimal money;

}
