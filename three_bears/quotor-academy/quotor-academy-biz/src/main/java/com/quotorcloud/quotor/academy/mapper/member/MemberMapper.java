package com.quotorcloud.quotor.academy.mapper.member;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.member.MemberDTO;
import com.quotorcloud.quotor.academy.api.entity.member.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.member.MemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 关联查询会员表和订单表并分页
     * @param page
     * @param memberDTO
     * @return
     */
    Page<MemberVO> selectMemberOrderPage(Page page, @Param("member") MemberDTO memberDTO);

    List<MemberVO> selectMemberOrderPage(@Param("member") MemberDTO memberDTO);

    MemberVO selectMemberById(@Param("id") String id);





}
