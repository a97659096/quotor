package com.quotorcloud.quotor.academy.service.impl.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageReturn;
import com.quotorcloud.quotor.academy.mapper.message.MessageReturnMapper;
import com.quotorcloud.quotor.academy.service.message.MessageReturnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 短信退还记录 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
@Service
public class MessageReturnServiceImpl extends ServiceImpl<MessageReturnMapper, MessageReturn> implements MessageReturnService {

}
