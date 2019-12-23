package com.quotorcloud.quotor.academy.service.condition;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.dto.condition.ConditionProDTO;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCategory;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionPro;
import com.quotorcloud.quotor.academy.api.vo.condition.ConditionProVO;
import com.quotorcloud.quotor.common.security.service.QuotorUser;

/**
 * <p>
 * 项目信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-08
 */
public interface ConditionProService extends IService<ConditionPro> {

    Boolean saveConditionPro(QuotorUser user, ConditionProDTO conditionProDTO);

    JSONObject listConditionPro(ConditionProDTO conditionProDTO);

    ConditionProVO selectProById(String id);

    Boolean removeConditionPro(QuotorUser user, String id);

    Boolean updateConditionPro(QuotorUser user, ConditionProDTO conditionProDTO, String id);

    String treeProByCategory(ConditionCategory conditionCategory);

}
