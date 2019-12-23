package com.quotorcloud.quotor.academy.service.message;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.entity.message.MessageOrder;
import com.quotorcloud.quotor.academy.api.entity.message.MessageRemind;
/**
 * <p>
 * 短信提醒 服务类
 * </p>
 *
 * @author houzw
 * @since 2019-12-06
 */
public interface MessageRemindService extends IService<MessageRemind> {
    /**
     * 查询短信提醒展示
     * @param messageRemind
     * @return
     */
    IPage listMessageRemind(Page page, MessageRemind messageRemind);

}
