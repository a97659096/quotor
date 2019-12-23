package com.quotorcloud.quotor.academy.mapper.member;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.entity.member.MemberIntegral;

/**
 * <p>
 * 积分记录表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
public interface MemberIntegralMapper extends BaseMapper<MemberIntegral> {

    /**
     * 查询会员积分
     * @param memberId
     * @return
     */
    Integer selectMemberIntegral(String memberId);

}
