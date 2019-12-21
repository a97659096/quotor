package com.quotorcloud.quotor.academy.service.condition;

import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardTypeTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 卡类型模板中间表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
public interface ConditionCardTypeTemplateService extends IService<ConditionCardTypeTemplate> {

    Boolean saveTypeTemplate(String typeId, String templateIds);
}
