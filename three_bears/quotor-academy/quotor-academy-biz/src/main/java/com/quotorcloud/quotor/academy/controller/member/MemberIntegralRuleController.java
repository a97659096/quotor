package com.quotorcloud.quotor.academy.controller.member;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.member.MemberIntegralRule;
import com.quotorcloud.quotor.academy.service.member.MemberIntegralRuleService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 积分规则 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
@RestController
@RequestMapping("/member/integral/rule")
public class MemberIntegralRuleController {

    @Autowired
    private MemberIntegralRuleService memberIntegralRuleService;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    /**
     * 新增员工积分规则
     * @param memberIntegralRule
     * @return
     */
    @PostMapping
    public R saveMemberIntegralRule(@RequestBody MemberIntegralRule memberIntegralRule){
        shopSetterUtil.shopSetter(memberIntegralRule, memberIntegralRule.getShopId());
        return R.ok(memberIntegralRuleService.save(memberIntegralRule));
    }

    /**
     * 修改员工积分规则
     * @param memberIntegralRule
     * @return
     */
    @PutMapping
    public R updateMemberIntegralRule(@RequestBody MemberIntegralRule memberIntegralRule){
        return R.ok(memberIntegralRuleService.updateById(memberIntegralRule));
    }

    /**
     * 删除员工积分规则
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeMemberIntegralRule(@PathVariable String id){
        return R.ok(memberIntegralRuleService.removeById(id));
    }

    /**
     * 分页查询员工积分规则
     * @param page
     * @param memberIntegralRule
     * @return
     */
    @GetMapping("list")
    public R listMemberIntegralRule(Page page, MemberIntegralRule memberIntegralRule){
        shopSetterUtil.shopSetter(memberIntegralRule, memberIntegralRule.getShopId());
        return R.ok(memberIntegralRuleService.page(page, Wrappers.<MemberIntegralRule>query()
                .lambda().eq(MemberIntegralRule::getShopId, memberIntegralRule.getShopId())));
    }
}
