package com.quotorcloud.quotor.academy.controller.message;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplateCategory;
import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateCategoryService;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateService;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 短信模板类别 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-02
 */
@RestController
@RequestMapping("/message/template/category")
public class MessageTemplateCategoryController {

    @Autowired
    private MessageTemplateCategoryService messageTemplateCategoryService;

    @Autowired
    private MessageTemplateService messageTemplateService;

    /**
     * 新增短信模板类别
     * @param messageTemplateCategory
     * @return
     */
    @PostMapping
    public R saveMessageCategory(@RequestBody MessageTemplateCategory messageTemplateCategory){
        return R.ok(messageTemplateCategoryService.save(messageTemplateCategory));
    }

    /**
     * 删除短信模板类别
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R updateMessageCategory(@PathVariable String id){
        int count = messageTemplateService.count(new QueryWrapper<MessageTemplate>().eq("template_category_id", id));
        if(count > 0){
            throw new MyException("此模板分类下还存在模板内容，删除失败");
        }
        return R.ok(messageTemplateCategoryService.removeById(id));
    }

    /**
     * 修改短信模板类别
     * @param messageTemplateCategory
     * @return
     */
    @PutMapping
    public R updateMessageCategory(@RequestBody MessageTemplateCategory messageTemplateCategory){
        return R.ok(messageTemplateCategoryService.updateById(messageTemplateCategory));
    }

    /**
     * 下拉框查询模板类别
     * @param shopId
     * @return
     */
    @GetMapping("listbox")
    public R listBoxMessageCategory(){
        return R.ok(messageTemplateCategoryService.listBoxMessageCategory());
    }

}
