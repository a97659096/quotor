package com.quotorcloud.quotor.academy.mapper.condition;

import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.condition.ConditionCardTypeVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 卡类型表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-09
 */
public interface ConditionCardTypeMapper extends BaseMapper<ConditionCardType> {

    ConditionCardTypeVO selectCardTypeVoByTypeId(@Param("typeId") String typeId);
}
