package com.quotorcloud.quotor.academy.api.vo.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import lombok.Data;

import java.util.List;


@Data
public class MessageCategoryTemplateVO {

    private String categoryId;

    /**
     * 类别名称
     */
    private String categoryName;

    private List<MessageTemplate> messageTemplates;

}
