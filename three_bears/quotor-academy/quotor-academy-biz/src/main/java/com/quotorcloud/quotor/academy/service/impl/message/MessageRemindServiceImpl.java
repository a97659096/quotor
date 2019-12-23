package com.quotorcloud.quotor.academy.service.impl.message;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.api.entity.message.MessageOrder;
import com.quotorcloud.quotor.academy.api.entity.message.MessageRemind;
import com.quotorcloud.quotor.academy.mapper.message.MessageRemindMapper;
import com.quotorcloud.quotor.academy.service.message.MessageRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信提醒表 服务实现类
 * </p>
 *
 * @author houzw
 * @since 2019-12-06
 */
@Service
public class MessageRemindServiceImpl extends ServiceImpl<MessageRemindMapper, MessageRemind> implements MessageRemindService {

    @Autowired
    private MessageRemindMapper messageRemindMapper;

    /**
     * 查询短信提醒展示
     *
     * @param page
     * @param messageRemind
     * @return
     */
    @Override
    public IPage listMessageRemind(Page page, MessageRemind messageRemind) {
        return messageRemindMapper.selectPage(page, new QueryWrapper<>());
    }
}
