package com.quotorcloud.quotor.academy.controller.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.message.MessageOrder;
import com.quotorcloud.quotor.academy.api.entity.message.MessageRemind;
import com.quotorcloud.quotor.academy.service.message.MessageRemindService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 短信提醒表 前端控制器
 * </p>
 *
 * @author houzw
 * @since 2019-12-06
 */
@RestController
@RequestMapping("/message/remind")
public class MessageRemindController {
    @Autowired
    private MessageRemindService messageRemindService;

    /**
     * 查询短信提醒列表
     * @param messageRemind
     * @return
     */
    @GetMapping("list")
    public R listOrder(Page page, MessageRemind messageRemind){
        return R.ok(messageRemindService.listMessageRemind(page, messageRemind));
    }
}
