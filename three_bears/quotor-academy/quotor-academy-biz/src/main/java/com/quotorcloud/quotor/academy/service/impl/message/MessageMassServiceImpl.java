package com.quotorcloud.quotor.academy.service.impl.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.message.submail.sdk.config.AppConfig;
import com.message.submail.sdk.lib.MessageMultiSend;
import com.message.submail.sdk.utils.ConfigLoader;
import com.quotorcloud.quotor.academy.api.entity.message.MessageMass;
import com.quotorcloud.quotor.academy.api.entity.message.MessageMassDetails;
import com.quotorcloud.quotor.academy.api.entity.message.MessageReturn;
import com.quotorcloud.quotor.academy.api.entity.message.MessageTemplate;
import com.quotorcloud.quotor.academy.api.vo.message.MessageMassVO;
import com.quotorcloud.quotor.academy.mapper.message.MessageMassMapper;
import com.quotorcloud.quotor.academy.service.message.MessageMassDetailsService;
import com.quotorcloud.quotor.academy.service.message.MessageMassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.service.message.MessageReturnService;
import com.quotorcloud.quotor.academy.service.message.MessageTemplateService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.admin.api.vo.DeptVO;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 短信群发记录表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
@Service
public class MessageMassServiceImpl extends ServiceImpl<MessageMassMapper, MessageMass> implements MessageMassService {

    @Autowired
    private MessageMassMapper messageMassMapper;

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private MessageMassDetailsService messageMassDetailsService;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Autowired
    private MessageReturnService messageReturnService;

    @Autowired
    private RemoteDeptService remoteDeptService;

    /**
     * 群发短信
     * @param messageMassVO
     * @return
     */
    @Override
    public Boolean saveMessageMassRecord(MessageMassVO messageMassVO) {
        //判断接收人不能为空
        List<MessageMassDetails> details = messageMassVO.getDetails();
        if(ComUtil.isEmpty(details)){
            throw new MyException("接收人不能为空哦！");
        }

        //查看短信余量是否充足
        QuotorUser user = SecurityUtils.getUser();
        R result = remoteDeptService.listDeptById(user.getDeptId());
        DeptVO deptVO = (DeptVO) result.getData();
        //查看短信数量是否足够发送，不足够抛出异常
        if(deptVO.getMessageCount() - details.size() < 0){
            throw new MyException("短信余量不足，请前往短信充值页面进行充值哦!");
        }

        //即将发送的内容
        String submailContent = getContent(messageMassVO);

        MessageMass messageMass = new MessageMass();
        messageMass.setShopId(String.valueOf(user.getDeptId()));
        messageMass.setShopName(user.getDeptName());
        messageMass.setTemplateId(messageMassVO.getTemplateId());
        messageMass.setContent(submailContent);
        messageMass.setSubmitTime(LocalDateTime.now());
        messageMass.setMessageCount(details.size());
        this.save(messageMass);

        //将接收人与手机号对应上
        Map<String, String> namePhoneMap = details.stream().collect(Collectors.toMap(MessageMassDetails::getReceivePhone,
                MessageMassDetails::getReceiveName));

        //类型设置为短信
        AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
        //群发短信类
        MessageMultiSend submail = new MessageMultiSend(config);
        //设置短信内容
        submail.addContent(submailContent);
        //TODO 后期有时间进行多线程优化，提高发送效率
        //遍历发送列表
        for (MessageMassDetails messageMassDetails : details) {
            submail.addMulti(messageMassDetails.getReceivePhone());
        }
        //发送短信
        String multixsend = submail.multixsend();
        //获取返回值
        JSONArray jsonArray = JSONArray.parseArray(multixsend);

        //TODO 后期有时间放到队列里去执行
        List<MessageMassDetails> messageMassDetailsList = new ArrayList<>();
        List<MessageReturn> messageReturns = new ArrayList<>();
        for(Object o:jsonArray){
            JSONObject jsonObject = (JSONObject) o;
            //封装短信详情对象
            MessageMassDetails messageMassDetails = new MessageMassDetails();
            messageMassDetails.setMassId(messageMass.getId());
            String phone = String.valueOf(jsonObject.get("to"));
            messageMassDetails.setReceivePhone(phone);
            messageMassDetails.setReceiveName(namePhoneMap.get(phone));
            //成功修改状态
            if(jsonObject.get("status").equals("success")){
                //状态设置为成功
                messageMassDetails.setReceiveState(1);
            }else {
                //失败
                messageMassDetails.setReceiveState(2);
                //退还短信
                MessageReturn messageReturn = MessageReturn.builder().content(submailContent)
                        .returnTime(LocalDateTime.now()).sendTime(LocalDateTime.now())
                        .name(namePhoneMap.get(phone)).phone(phone).returnCount(1).build();
                messageReturns.add(messageReturn);
            }
            messageMassDetailsList.add(messageMassDetails);
        }
        //短信群发详情插入
        if(!ComUtil.isEmpty(messageMassDetailsList)) {
            messageMassDetailsService.saveBatch(messageMassDetailsList);
        }
        //短信退还记录
        if(!ComUtil.isEmpty(messageReturns)){
            messageReturnService.saveBatch(messageReturns);
        }
        //花费的短信数目
        int expendMessage = messageMassDetailsList.size() - messageReturns.size();
        //短信扣除
        if(expendMessage > 0){
            //TODO 数量记录先不加
            DeptDTO deptDTO = new DeptDTO();
            deptDTO.setDeptId(user.getDeptId());
            deptDTO.setMessageCount(expendMessage);
            remoteDeptService.updateDept(deptDTO);
        }
        return Boolean.TRUE;
    }

    private String getContent(MessageMassVO messageMassVO) {
        //首先根据模板id查出模板
        MessageTemplate messageTemplate = messageTemplateService.getById(messageMassVO.getTemplateId());
        String submailContent = messageTemplate.getSubmailContent();
        //把变量替换成传入的变量
        Map<String, String> variableMap = messageMassVO.getVariables();
        if(!ComUtil.isEmpty(variableMap)){
            for (String variable:variableMap.keySet()){
                //替换变量内容
                submailContent.replace("@var("+variable+")", variableMap.get(variable));
            }
        }
        return submailContent;
    }
}
