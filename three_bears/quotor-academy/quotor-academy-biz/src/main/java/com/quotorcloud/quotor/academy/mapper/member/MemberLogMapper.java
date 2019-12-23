package com.quotorcloud.quotor.academy.mapper.member;

import com.quotorcloud.quotor.academy.api.dto.member.MemberLogDTO;
import com.quotorcloud.quotor.academy.api.entity.member.MemberLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.member.MemberLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户护理日志 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
public interface MemberLogMapper extends BaseMapper<MemberLog> {

    List<MemberLogVO> selectMemberLog(@Param("memberLog") MemberLogDTO memberLogDTO);

//    List<MemberLogVO> selectMemberLogByMemberId(@Param("memberId") String memberId);
}
