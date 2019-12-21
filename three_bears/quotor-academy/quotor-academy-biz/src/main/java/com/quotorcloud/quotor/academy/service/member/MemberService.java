package com.quotorcloud.quotor.academy.service.member;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.dto.member.MemberDTO;
import com.quotorcloud.quotor.academy.api.entity.member.Member;
import com.quotorcloud.quotor.academy.api.vo.member.MemberVO;

import java.util.List;

/**
 * <p>
 * 客户信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
public interface MemberService extends IService<Member> {

    Boolean saveMember(MemberDTO memberDTO);

    Page<MemberVO> listMember(Page page, MemberDTO memberDTO);

    Boolean updateMember(MemberDTO memberDTO);

    Boolean removeMember(String id);

    MemberVO getMemberById(String id);

    List<JSONObject> selectMemberListBox(String shopId);

    //计算会员总个数
    JSONObject countMemberTimes(MemberDTO memberDTO);

    /**
     * 计算会员总个数并根据店铺标识分组展示，并且计算出会员增长率
     * @param memberDTO
     * @return
     */
    JSONObject countMemberTimesGroupByShopId(MemberDTO memberDTO);

}
