package com.quotorcloud.quotor.academy.mapper.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.message.MessageCategoryTemplateVO;

import java.util.List;

/**
 * <p>
 * 短信模板内容 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-07
 */
public interface MessageTemplateMapper extends BaseMapper<MessageTemplate> {

    List<MessageCategoryTemplateVO> listMessageCategoryTemplate();
}
