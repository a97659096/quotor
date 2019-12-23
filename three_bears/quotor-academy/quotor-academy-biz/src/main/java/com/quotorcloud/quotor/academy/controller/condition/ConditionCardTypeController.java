package com.quotorcloud.quotor.academy.controller.condition;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardType;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardTypeTemplate;
import com.quotorcloud.quotor.academy.service.condition.ConditionCardTypeService;
import com.quotorcloud.quotor.academy.service.condition.ConditionCardTypeTemplateService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * <p>
 * 卡类型表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
@RestController
@RequestMapping("/condition/card/type")
public class ConditionCardTypeController {

    @Autowired
    private ConditionCardTypeService conditionCardTypeService;

    @Autowired
    private ConditionCardTypeTemplateService conditionCardTypeTemplateService;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    /**
     * 新增卡类别
     * @param conditionCardType
     * @return
     */
    @PostMapping
    public R saveConditionCardType(@RequestBody ConditionCardType conditionCardType){
        //设置店铺信息
        shopSetterUtil.shopSetter(conditionCardType, conditionCardType.getShopId());
        //插入操作
        return R.ok(conditionCardTypeService.save(conditionCardType));
    }

    /**
     * 更新类型模板
     * @param typeId
     * @param templateIds
     * @return
     */
    @PutMapping
    public R updateConditionCardTemplate(String typeId, String templateIds){
        return R.ok(conditionCardTypeTemplateService.saveTypeTemplate(typeId, templateIds));
    }

    /**
     * 返回类型下的模板标识
     * @param id
     * @return
     */
    @GetMapping("list/{id}")
    public R listTemplateByTypeId(@PathVariable String id){
        return R.ok(conditionCardTypeTemplateService.list(Wrappers.<ConditionCardTypeTemplate>query()
                .lambda().eq(ConditionCardTypeTemplate::getTypeId, id))
                .stream().map(ConditionCardTypeTemplate::getTemplateId).collect(Collectors.toList()));
    }

}
