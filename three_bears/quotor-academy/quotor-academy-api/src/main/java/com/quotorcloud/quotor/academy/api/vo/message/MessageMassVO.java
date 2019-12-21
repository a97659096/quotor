package com.quotorcloud.quotor.academy.api.vo.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageMassDetails;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MessageMassVO {

    private String templateId;

    private Map<String, String> variables;

    private List<MessageMassDetails> details;

}
