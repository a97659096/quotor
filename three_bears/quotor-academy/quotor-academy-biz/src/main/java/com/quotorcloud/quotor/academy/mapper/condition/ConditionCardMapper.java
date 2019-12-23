package com.quotorcloud.quotor.academy.mapper.condition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.condition.ConditionCardDTO;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.condition.ConditionCardVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 卡片信息 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
public interface ConditionCardMapper extends BaseMapper<ConditionCard> {

    IPage<ConditionCardVO> selectCardPage(Page page,
                                          @Param("card") ConditionCardDTO card);

    ConditionCardVO selectCardById(@Param("id") String id);

}
