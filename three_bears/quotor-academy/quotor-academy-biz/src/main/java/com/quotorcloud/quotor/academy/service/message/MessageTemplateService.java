package com.quotorcloud.quotor.academy.service.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.message.MessageCategoryTemplateVO;
import com.quotorcloud.quotor.common.core.util.R;

import java.util.List;

/**
 * <p>
 * 短信模板内容 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-02
 */
public interface MessageTemplateService extends IService<MessageTemplate> {

    List<MessageCategoryTemplateVO> listMessageCategoryTemplate(String shopId);

    R updateMessageTemplate(MessageTemplate messageTemplate);

    Boolean removeMessageTemplate(String id);

    R saveMessageTemplate(MessageTemplate messageTemplate);
}
