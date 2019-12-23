package com.quotorcloud.quotor.academy.controller.submail;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotorcloud.quotor.academy.api.entity.message.MessageMassDetails;
import com.quotorcloud.quotor.academy.api.entity.message.MessageReturn;
import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import com.quotorcloud.quotor.academy.service.message.MessageMassDetailsService;
import com.quotorcloud.quotor.academy.service.message.MessageReturnService;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateService;
import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@RestController
@RequestMapping("submail")
public class SubmailController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private MessageMassDetailsService messageMassDetailsService;

    @Autowired
    private RemoteDeptService remoteDeptService;

    @Autowired
    private MessageReturnService messageReturnService;

    /**
     * 审核回调接口，供短信平台访问
     * @param request
     * @throws IOException
     */
    @PostMapping("template_check_callback")
    public void templateCheckCallback(HttpServletRequest request) throws IOException {
        InputStream inputStream =  request.getInputStream();

        //BufferedReader是包装设计模式，性能更搞
        BufferedReader in =  new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line ;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();
        //将返回值转成json格式 TODO 后期设计模式优化考虑
        JSONObject jsonObject = JSON.parseObject(sb.toString());
        if(jsonObject.get("events").equals("template_accept") || jsonObject.get("events").equals("template_reject")){
            MessageTemplate messageTemplate = new MessageTemplate();
            messageTemplate.setTemplateId((String) jsonObject.get("template_id"));
            messageTemplate.setTriggerTime((String) jsonObject.get("timestamp"));
            messageTemplate.setToken((String) jsonObject.get("token"));
            messageTemplate.setSignature((String) jsonObject.get("signature"));
            //通过
            if(jsonObject.get("events").equals("template_accept")){
                //审核通过
                messageTemplate.setCheckState(1);
                messageTemplate.setPassTime(LocalDateTime.now());
                //未通过
            }else if(jsonObject.get("events").equals("template_reject")){
                //审核未通过
                messageTemplate.setCheckState(2);
                messageTemplate.setFailCause((String) jsonObject.get("reason"));
            }
            //进行数据库修改操作
            messageTemplateService.update(messageTemplate, new QueryWrapper<MessageTemplate>().lambda()
                    .eq(MessageTemplate::getTemplateId, messageTemplate.getTemplateId()));
        }
        //短信发送失败
        if(jsonObject.get("events").equals("dropped")){
            String sendId = (String) jsonObject.get("send_id");
            String phone = (String) jsonObject.get("address");
            //群发失败
            MessageMassDetails messageMassDetails = messageMassDetailsService.getOne(new QueryWrapper<MessageMassDetails>().eq("mmd_send_id", sendId)
                    .eq("mmd_receive_phone", phone));
            if(!ComUtil.isEmpty(messageMassDetails)){
                //发送失败
                messageMassDetails.setReceiveState(2);
                //放入短信退还中
                MessageReturn messageReturn = MessageReturn.builder().sendTime(messageMassDetails.getGmtCreate()).returnCount(1).phone(messageMassDetails.getReceivePhone())
                        .name(messageMassDetails.getReceiveName()).returnTime(LocalDateTime.now()).content(null).shopId(messageMassDetails.getShopId()).build();
                messageReturnService.save(messageReturn);
                //增加店铺短信数量
                DeptDTO deptDTO = new DeptDTO();
                deptDTO.setDeptId(Integer.valueOf(messageMassDetails.getShopId()));
                deptDTO.setMessageCount(1);
                remoteDeptService.updateDept(deptDTO);
            }
            //TODO 通知短信失败

        }
    }
}
