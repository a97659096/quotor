package com.quotorcloud.quotor.academy.mapper.member;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.member.MemberCardVO;
import com.quotorcloud.quotor.academy.api.vo.order.OrderWebVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员卡信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface MemberCardMapper extends BaseMapper<MemberCard> {


    List<MemberCardVO> listMemberCard(@Param("card") MemberCard memberCard);

    /**
     * 分页查询卡信息
     */
    IPage<MemberCardVO> listMemberCard(Page page, @Param("card")MemberCard memberCard);

    /**
     * 根据id查询会员持卡详情
     * @param id
     * @return
     */
    MemberCardVO selectMemberCardById(@Param("id") String id);

}
