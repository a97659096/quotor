package com.quotorcloud.quotor.academy.controller.condition;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.condition.ConditionCardDTO;
import com.quotorcloud.quotor.academy.service.condition.ConditionCardService;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 卡片信息 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@RestController
@RequestMapping("/condition/card")
public class ConditionCardController {

    @Autowired
    private ConditionCardService conditionCardService;

    /**
     * 新增会员卡
     * @param conditionCardDTO
     * @return
     */
    @PostMapping
    public R saveCard(@RequestBody ConditionCardDTO conditionCardDTO){
        QuotorUser quotorUser = SecurityUtils.getUser();
        return R.ok(conditionCardService.saveConditionCard(quotorUser, conditionCardDTO));
    }

    /**
     * 修改会员卡信息
     * @param conditionCardDTO
     * @return
     */
    @PutMapping
    public R updateCard(@RequestBody ConditionCardDTO conditionCardDTO){
        QuotorUser quotorUser = SecurityUtils.getUser();
        return R.ok(conditionCardService.updateConditionCard(quotorUser, conditionCardDTO));
    }

    /**
     * 分页查询会员卡信息
     */
    @GetMapping("list")
    public R listCard(Page page, ConditionCardDTO conditionCardDTO){
        return R.ok(conditionCardService.selectConditionCard(page, conditionCardDTO));
    }

    @DeleteMapping("{id}")
    public R removeCard(@PathVariable String id){
        QuotorUser user = SecurityUtils.getUser();
        return R.ok(conditionCardService.removeConditionCard(user, id));
    }
}
