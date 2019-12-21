package com.quotorcloud.quotor.academy.controller.condition;


import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCategory;
import com.quotorcloud.quotor.academy.service.condition.ConditionCategoryService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 品项类别信息维护 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-07
 */
@RestController
@RequestMapping("/condition/category")
public class ConditionCategoryController {

    @Autowired
    private ConditionCategoryService conditionCategoryService;

    @PostMapping
    public R saveConditionCategory(@RequestBody ConditionCategory conditionCategory){
        return R.ok(conditionCategoryService.saveConditionCategory(conditionCategory));
    }

    @GetMapping("tree")
    public R listTreeCategoryByShopId(ConditionCategory conditionCategory){
        return R.ok(conditionCategoryService.getCategoryTree(conditionCategory));
    }

    @PutMapping
    public R updateCategoryById(@RequestBody ConditionCategory conditionCategory){
        return R.ok(conditionCategoryService.updateCategory(conditionCategory));
    }

    @DeleteMapping("{id}")
    public R deleteCategoryById(@PathVariable String id){
        return R.ok(conditionCategoryService.removeCategory(id));
    }
}
