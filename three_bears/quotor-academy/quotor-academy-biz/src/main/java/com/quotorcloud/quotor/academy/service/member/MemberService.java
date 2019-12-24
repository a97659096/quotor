package com.quotorcloud.quotor.academy.service.member;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.dto.member.MemberDTO;
import com.quotorcloud.quotor.academy.api.dto.order.OrderIncomeDTO;
import com.quotorcloud.quotor.academy.api.entity.member.Member;
import com.quotorcloud.quotor.academy.api.vo.member.MemberVO;
import com.quotorcloud.quotor.common.security.service.QuotorUser;

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

    Boolean saveMember(QuotorUser user, MemberDTO memberDTO);

    Page<MemberVO> listMember(Page page, MemberDTO memberDTO);

    Boolean updateMember(QuotorUser quotorUser, MemberDTO memberDTO, String id);

    Boolean removeMember(QuotorUser user, String id);

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

    //按照入会时间查找会员总个数
    MemberDTO countMemberTime(MemberDTO memberDTO);

    //消费人次
    Integer countConsumer(String id);
    /**
     * 计算近一月，近三月，近一年增长的会员个数
     */
    JSONObject selectOrderRecently(MemberDTO meberDTO);

}
