package com.quotorcloud.quotor.academy.service.impl.condition;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Splitter;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardTypeTemplate;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionCardTypeTemplateMapper;
import com.quotorcloud.quotor.academy.service.condition.ConditionCardTypeTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 卡类型模板中间表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
@Service
public class ConditionCardTypeTemplateServiceImpl extends ServiceImpl<ConditionCardTypeTemplateMapper, ConditionCardTypeTemplate> implements ConditionCardTypeTemplateService {

    @Override
    public Boolean saveTypeTemplate(String typeId, String templateIds) {
        this.remove(Wrappers.<ConditionCardTypeTemplate>query().lambda()
                .eq(ConditionCardTypeTemplate::getTypeId, typeId));

        if(!ComUtil.isEmpty(templateIds)){
            List<String> tempIds = Splitter.on(CommonConstants.SEPARATOR).splitToList(templateIds);
            List<ConditionCardTypeTemplate> typeTemplates = tempIds.stream().map(tempId -> {
                ConditionCardTypeTemplate conditionCardTypeTemplate = new ConditionCardTypeTemplate();
                conditionCardTypeTemplate.setTypeId(typeId);
                conditionCardTypeTemplate.setTemplateId(tempId);
                return conditionCardTypeTemplate;
            }).collect(Collectors.toList());
            this.saveBatch(typeTemplates);
        }
        return Boolean.TRUE;
    }
}
