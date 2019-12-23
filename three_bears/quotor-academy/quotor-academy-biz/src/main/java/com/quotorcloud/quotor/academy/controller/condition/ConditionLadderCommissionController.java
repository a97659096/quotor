package com.quotorcloud.quotor.academy.controller.condition;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionLadderCommission;
import com.quotorcloud.quotor.academy.service.condition.ConditionLadderCommissionService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 阶梯提成表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
@RestController
@RequestMapping("/condition/ladder/commission")
public class ConditionLadderCommissionController {

    @Autowired
    private ConditionLadderCommissionService conditionLadderCommissionService;

    /**
     * 新增阶梯提成规则
     * @param conditionLadderCommission
     * @return
     */
    @PostMapping
    public R saveLadderCommission(@RequestBody ConditionLadderCommission conditionLadderCommission){
        return R.ok(conditionLadderCommissionService.saveConditionLadderCommission(conditionLadderCommission));
    }

    /**
     * 修改阶梯提成规则
     * @param conditionLadderCommission
     * @return
     */
    @PutMapping
    public R updateLadderCommission(@RequestBody ConditionLadderCommission conditionLadderCommission){
        return R.ok(conditionLadderCommissionService.updateConditionLadderCommission(conditionLadderCommission));
    }

    /**
     * 删除阶梯提成规则
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeLadderCommission(@PathVariable String id){
        return R.ok(conditionLadderCommissionService.removeConditionLadderCommission(id));
    }

    /**
     * 分页查询阶梯提成规则
     * @param page
     * @param conditionLadderCommission
     * @return
     */
    @GetMapping("list")
    public R listLaddderCommission(Page page, ConditionLadderCommission conditionLadderCommission){
        return R.ok(conditionLadderCommissionService.listPageConditionLadderCommission(page, conditionLadderCommission));
    }
}
