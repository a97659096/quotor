package com.quotorcloud.quotor.academy.service.member;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.member.MemberDTO;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.member.MemberCardVO;

import java.util.List;

/**
 * <p>
 * 会员卡信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface MemberCardService extends IService<MemberCard> {

    /**
     * 查询会员下边的卡详情
     * @param memberCard
     * @return
     */
    List<MemberCardVO> listMemberCardByMemberId(MemberCard memberCard);


    /**
     * 计算总余量
     * @param memberCard
     * @return
     */
    JSONObject countTotalCardMargin(MemberCard memberCard);

    /**
     * 计算总余量根据门店分组
     */
    JSONObject countTotalCardMarginGroupByShopId(MemberCard memberCard);

    /**
     * 计算总余量根据会员分组
     */
    JSONObject countTotalCardMarginGroupByMemberId(MemberCard memberCard);

}
