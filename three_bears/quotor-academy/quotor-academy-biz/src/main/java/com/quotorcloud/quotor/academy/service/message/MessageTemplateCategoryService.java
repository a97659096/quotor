package com.quotorcloud.quotor.academy.service.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplateCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 短信模板类别 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-02
 */
public interface MessageTemplateCategoryService extends IService<MessageTemplateCategory> {

    List<MessageTemplateCategory> listBoxMessageCategory();
}
