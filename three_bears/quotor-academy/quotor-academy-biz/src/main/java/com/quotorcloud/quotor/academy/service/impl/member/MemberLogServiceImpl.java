package com.quotorcloud.quotor.academy.service.impl.member;

import com.quotorcloud.quotor.academy.api.dto.member.MemberLogDTO;
import com.quotorcloud.quotor.academy.api.entity.member.MemberLog;
import com.quotorcloud.quotor.academy.api.vo.member.MemberLogVO;
import com.quotorcloud.quotor.academy.mapper.member.MemberLogMapper;
import com.quotorcloud.quotor.academy.service.member.MemberLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户护理日志 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@Service
public class MemberLogServiceImpl extends ServiceImpl<MemberLogMapper, MemberLog> implements MemberLogService {

    @Autowired
    private MemberLogMapper memberLogMapper;

    @Override
    public Boolean saveMemberLog(MemberLogDTO memberLogDTO) {
        MemberLog memberLog = new MemberLog();
        BeanUtils.copyProperties(memberLogDTO, memberLog);
        FileUtil.saveFileAndField(memberLog, memberLogDTO, "img", FileConstants.FileType.FILE_MEMBER_LOG_IMG_DIR, null);
        memberLogMapper.insert(memberLog);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateMemberLog(MemberLogDTO memberLogDTO) {
        MemberLog memberLog = memberLogMapper.selectById(memberLogDTO.getId());
        BeanUtils.copyProperties(memberLogDTO ,memberLog);
        FileUtil.removeFileAndField(memberLog, memberLogDTO, "img", FileConstants.FileType.FILE_MEMBER_LOG_IMG_DIR);
        FileUtil.saveFileAndField(memberLog, memberLogDTO, "img", FileConstants.FileType.FILE_MEMBER_LOG_IMG_DIR, null);
        memberLogMapper.updateById(memberLog);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeMemberLog(String id) {
        memberLogMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public List<MemberLogVO> selectMemberLogVO(MemberLogDTO memberLogDTO) {
        List<MemberLogVO> memberLogVOS = memberLogMapper.selectMemberLog(memberLogDTO);
        memberLogVOS.forEach(memberLogVO -> memberLogVO.setImg(FileUtil.getJsonObjects(memberLogVO.getImgDatabase())));
        return memberLogVOS;
    }
}
