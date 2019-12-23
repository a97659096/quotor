package com.quotorcloud.quotor.academy.controller.message;


import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 短信模板内容 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-02
 */
@RestController
@RequestMapping("/message/template")
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;



    /**
     * 新增短信模板
     * @param messageTemplate
     * @return
     */
    @PostMapping
    public R saveMessageTemplate(@RequestBody MessageTemplate messageTemplate){
        return R.ok(messageTemplateService.saveMessageTemplate(messageTemplate));
    }

    /**
     * 修改短信模板
     * @param messageTemplate
     * @return
     */
    @PutMapping
    public R updateMessageTemplate(@RequestBody MessageTemplate messageTemplate){
        return R.ok(messageTemplateService.updateMessageTemplate(messageTemplate));
    }

    /**
     * 删除短信模板
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeMessageTemplate(@PathVariable String id){
        return R.ok(messageTemplateService.removeMessageTemplate(id));
    }

    /**
     * 查询短信模板 和类别关联查询
     * @param shopId
     * @return
     */
    @GetMapping("list")
    public R listMessageTemplate(String shopId){
        return R.ok(messageTemplateService.listMessageCategoryTemplate(shopId));
    }



}
