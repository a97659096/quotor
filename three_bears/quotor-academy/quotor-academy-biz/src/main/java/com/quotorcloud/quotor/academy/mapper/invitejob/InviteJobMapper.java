package com.quotorcloud.quotor.academy.mapper.invitejob;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.invitejob.InviteJobDTO;
import com.quotorcloud.quotor.academy.api.entity.invitejob.InviteJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.invitejob.InviteJobVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 招聘下店信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-04
 */
public interface InviteJobMapper extends BaseMapper<InviteJob> {

    //查询招聘信息列表
    IPage<InviteJobVO> listInviteJob(Page<InviteJobVO> page, @Param("ij") InviteJobDTO inviteJobDTO);

    //查询求职信息列表
    IPage<InviteJobVO> listFindJob(Page<InviteJobVO> page, @Param("ij")InviteJobDTO inviteJobDTO);

    //根据id查询招聘信息
    InviteJobVO selectInviteJobById(@Param("id") String id);

    //根据id查询求职信息
    InviteJobVO selectFindJobById(@Param("id") String id);
}
