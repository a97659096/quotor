package com.quotorcloud.quotor.academy.controller.condition;


import com.alibaba.fastjson.JSON;
import com.quotorcloud.quotor.academy.api.dto.condition.ConditionProDTO;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCategory;
import com.quotorcloud.quotor.academy.service.condition.ConditionProService;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 项目信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-08
 */
@RestController
@RequestMapping("/condition/pro")
public class ConditionProController {

    @Autowired
    private ConditionProService conditionProService;

    @PostMapping
    public R saveConditionPro(ConditionProDTO conditionProDTO){
        QuotorUser user = SecurityUtils.getUser();
        return R.ok(conditionProService.saveConditionPro(user, conditionProDTO));
    }

    @GetMapping("list")
    public R listConditionPro(ConditionProDTO conditionProDTO){
        return R.ok(conditionProService.listConditionPro(conditionProDTO));
    }

    @PutMapping
    public R updateConditionPro(ConditionProDTO conditionProDTO){
        QuotorUser user = SecurityUtils.getUser();
        return R.ok(conditionProService.updateConditionPro(user,conditionProDTO,conditionProDTO.getId()));
    }

    @DeleteMapping("/{id}")
    public R removeConditionProById(@PathVariable String id){
        QuotorUser user = SecurityUtils.getUser();
        return R.ok(conditionProService.removeConditionPro(user, id));
    }

    @GetMapping("list/{id}")
    public R selectConditionProById(@PathVariable String id){
        return R.ok(conditionProService.selectProById(id));
    }

    @GetMapping("list/category/tree")
    public R treeConditionPro(ConditionCategory conditionCategory){
        return R.ok(JSON.parse(conditionProService.treeProByCategory(conditionCategory)));
    }
}
