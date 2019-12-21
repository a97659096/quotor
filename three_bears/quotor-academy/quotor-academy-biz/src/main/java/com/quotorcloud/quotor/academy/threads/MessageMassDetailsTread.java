package com.quotorcloud.quotor.academy.threads;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.message.submail.sdk.config.AppConfig;
import com.message.submail.sdk.lib.MessageMultiSend;
import com.message.submail.sdk.utils.ConfigLoader;
import com.quotorcloud.quotor.academy.api.entity.message.MessageMassDetails;

import java.util.Collections;
import java.util.List;

public class MessageMassDetailsTread implements Runnable {

    private List<MessageMassDetails> messageMassDetailsList;

    private String content;

    private List<JSONObject> responseJsonList;

    public MessageMassDetailsTread(){
        super();
    }

    public MessageMassDetailsTread(List<MessageMassDetails> messageMassDetailsList, String content, List<JSONObject> jsonObjects){
        super();
        this.messageMassDetailsList = messageMassDetailsList;
        this.content = content;
        //设置为线程安全的list
        this.responseJsonList = Collections.synchronizedList(jsonObjects);
    }

    /**
     * run方法执行发送短信
     */
    @Override
    public void run() {
        //类型设置为短信
        AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
        //群发短信类
        MessageMultiSend submail = new MessageMultiSend(config);
        //设置内容
        submail.addContent(content);
        //设置店铺
        for (MessageMassDetails messageMassDetails : messageMassDetailsList){
            submail.addTo(messageMassDetails.getReceivePhone());
        }
        //群发操作，返回结果
        String response = submail.multixsend();
        //添加进存放结果集的结合，方便后边做存储操作
        responseJsonList.add(JSON.parseObject(response));
    }
}
