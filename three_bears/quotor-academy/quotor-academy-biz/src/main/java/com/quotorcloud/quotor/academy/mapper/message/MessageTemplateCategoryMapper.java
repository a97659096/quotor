package com.quotorcloud.quotor.academy.mapper.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplateCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 短信模板类别 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-02
 */
public interface MessageTemplateCategoryMapper extends BaseMapper<MessageTemplateCategory> {

    List<MessageTemplateCategory> listBoxByShopId();

}
