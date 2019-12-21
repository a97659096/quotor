package com.quotorcloud.quotor.academy.service.impl.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplateCategory;
import com.quotorcloud.quotor.academy.mapper.message.MessageTemplateCategoryMapper;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 短信模板类别 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-02
 */
@Service
public class MessageTemplateCategoryServiceImpl extends ServiceImpl<MessageTemplateCategoryMapper, MessageTemplateCategory> implements MessageTemplateCategoryService {

    @Autowired
    private MessageTemplateCategoryMapper messageTemplateCategoryMapper;

    @Override
    public List<MessageTemplateCategory> listBoxMessageCategory() {
        return messageTemplateCategoryMapper.listBoxByShopId();
    }
}
