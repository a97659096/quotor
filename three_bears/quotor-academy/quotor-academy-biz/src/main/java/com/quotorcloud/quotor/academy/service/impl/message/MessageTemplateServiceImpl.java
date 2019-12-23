package com.quotorcloud.quotor.academy.service.impl.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.message.submail.sdk.config.AppConfig;
import com.message.submail.sdk.utils.ConfigLoader;
import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplateVariable;
import com.quotorcloud.quotor.academy.api.vo.message.MessageCategoryTemplateVO;
import com.quotorcloud.quotor.academy.mapper.message.MessageTemplateMapper;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateVariableService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 短信模板内容 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-07
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplate> implements MessageTemplateService {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    @Autowired
    private MessageTemplateVariableService messageTemplateVariableService;

    @Override
    public List<MessageCategoryTemplateVO> listMessageCategoryTemplate(String shopId) {
        return messageTemplateMapper.listMessageCategoryTemplate();
    }

    @Override
    @Transactional
    public R updateMessageTemplate(MessageTemplate messageTemplate) {
        //向短信平台发送申请信息
        com.message.submail.sdk.lib.MessageTemplate submail = getMessageTemplate();
        String originalContent = messageTemplate.getOriginalContent();
        //获取变量
        List<String> variables = StringUtil.extractMessageByRegular(originalContent);

        submail.putTemplateId(messageTemplate.getTemplateId());
        //根据变量去替换成拼音缩写
        submail.putSmsContent(getSubmailContent(originalContent, variables));
        submail.putSmsSignature("【三只熊科技】");
        String response = submail.putTemplate();
        JSONObject responseJSON = JSON.parseObject(response);
        //失败抛出异常
        if(responseJSON.get("status").equals("error")){
            return R.failed(String.valueOf(responseJSON.get("msg")));
        }else{
            //审核中
            messageTemplate.setCheckState(3);
            messageTemplateMapper.updateById(messageTemplate);

            //删除之前的变量信息
            messageTemplateVariableService.remove(new QueryWrapper<MessageTemplateVariable>()
                    .eq("mtv_template_id", messageTemplate.getId()));
            //存储变量
            saveVariable(variables, messageTemplate.getId());
            return R.ok();
        }
    }

    @Override
    public Boolean removeMessageTemplate(String id) {
        messageTemplateMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public R saveMessageTemplate(MessageTemplate messageTemplate) {
        //向短信平台发送申请信息
        com.message.submail.sdk.lib.MessageTemplate submail = getMessageTemplate();

        String content = messageTemplate.getOriginalContent();
        List<String> variables = StringUtil.extractMessageByRegular(content);

        String submailContent = getSubmailContent(content, variables);

        //保存带变量的短信信息
        messageTemplate.setSubmailContent(submailContent);

        //设置信息
        submail.postSmsContent(submailContent);
        submail.postSmsSignature("【三只熊科技】");
        //发送请求
        String response = submail.postTemplate();

        JSONObject responseJson = JSON.parseObject(response);
        //是否返回成功
        if(responseJson.get("status").equals("error")){
            return R.failed(String.valueOf(responseJson.get("msg")));
        }else{
            String templateId = String.valueOf(responseJson.get("template_id"));
            //成功把模板id保存起来
            messageTemplate.setTemplateId(templateId);
            //未删除
            messageTemplate.setDelState(CommonConstants.STATUS_NORMAL);
            //审核中
            messageTemplate.setCheckState(3);

            //存储模板信息到数据库
            this.save(messageTemplate);

            //存储变量
            saveVariable(variables, messageTemplate.getId());

            return R.ok();
        }
    }

    //获取submail 模板
    private com.message.submail.sdk.lib.MessageTemplate getMessageTemplate() {
        AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
        return new com.message.submail.sdk.lib.MessageTemplate(config);
    }

    //存储变量信息
    private void saveVariable(List<String> variables, String templateId) {
        List<MessageTemplateVariable> messageTemplateVariables = new ArrayList<>();
        for (String var:variables){
            //设置变量对象
            MessageTemplateVariable messageTemplateVariable = new MessageTemplateVariable();
            messageTemplateVariable.setTemplateId(templateId);
            messageTemplateVariable.setName(var);
            messageTemplateVariable.setAcronym(StringUtil.getPingYinSuoXie(var));
            messageTemplateVariables.add(messageTemplateVariable);
        }
        //对变量进行批量插入
        messageTemplateVariableService.saveBatch(messageTemplateVariables);
    }

    //获取修改变量后的内容，用于去申请短信模板
    private String getSubmailContent(String originalContent, List<String> variables) {
        String submaiContent = originalContent;
        //首先把变量值取出来，换成字母缩写
        //获取括号中内容的集合
        //判断是否有重复的元素，有的话抛出异常
        long count = variables.stream().distinct().count();
        if(count < variables.size()){
            throw new MyException("变量参数不可重名，请重新输入");
        }
        for (String variable:variables){
            //将内容从[客户名称]替换为@var(khmc)
            submaiContent = submaiContent.replace("[" + variable + "]", "@var("+ StringUtil.getPingYinSuoXie(variable)+")");
        }
        return submaiContent;
    }

}
