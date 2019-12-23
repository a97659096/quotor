package com.quotorcloud.quotor.academy.service.condition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.condition.ConditionCardDTO;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.condition.ConditionCardVO;
import com.quotorcloud.quotor.common.security.service.QuotorUser;

/**
 * <p>
 * 卡片信息 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
public interface ConditionCardService extends IService<ConditionCard> {

    Boolean saveConditionCard(QuotorUser quotorUser, ConditionCardDTO conditionCardDTO);

    IPage<ConditionCardVO> selectConditionCard(Page page, ConditionCardDTO conditionCardDTO);

    Boolean updateConditionCard(QuotorUser quotorUser, ConditionCardDTO conditionCardDTO, String id);

    Boolean removeConditionCard(QuotorUser user, String id);
}
