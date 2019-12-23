package com.quotorcloud.quotor.academy.api.vo.condition;


import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardTemplate;
import lombok.Data;

import java.util.List;

@Data
public class ConditionCardTypeVO {

    private String id;

    private String name;

    private Integer type;

    private Integer way;

    private String shopId;

    private String shopName;

    private List<ConditionCardTemplate> conditionCardTemplates;
}
