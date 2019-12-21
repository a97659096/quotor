package com.quotorcloud.quotor.academy.controller.condition;


import com.quotorcloud.quotor.academy.api.entity.condition.ConditionContentCommission;
import com.quotorcloud.quotor.academy.service.condition.ConditionContentCommissionService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 项目产品卡片提成表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/condition/content/commission")
public class ConditionContentCommissionController {

    @Autowired
    private ConditionContentCommissionService conditionContentCommissionService;

    @PostMapping
    public R setContentCommission(ConditionContentCommission conditionContentCommission){
        return null;
    }

}
