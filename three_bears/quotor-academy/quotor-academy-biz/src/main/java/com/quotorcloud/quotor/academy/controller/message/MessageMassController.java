package com.quotorcloud.quotor.academy.controller.message;


import com.quotorcloud.quotor.academy.service.message.MessageMassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 短信群发记录表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
@RestController
@RequestMapping("/message/mass")
public class MessageMassController {

    @Autowired
    private MessageMassService messageMassService;

}
