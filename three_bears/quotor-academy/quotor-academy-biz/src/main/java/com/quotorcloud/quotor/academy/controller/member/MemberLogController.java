package com.quotorcloud.quotor.academy.controller.member;


import com.quotorcloud.quotor.academy.api.dto.member.MemberLogDTO;
import com.quotorcloud.quotor.academy.service.member.MemberLogService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 客户护理日志 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/member/log")
public class MemberLogController {

    @Autowired
    private MemberLogService memberLogService;

    @PostMapping
    public R saveMemberLog(MemberLogDTO memberLogDTO){
        return R.ok(memberLogService.saveMemberLog(memberLogDTO));
    }

    @PutMapping
    public R updateMemberLog(MemberLogDTO memberLogDTO){
        return R.ok(memberLogService.updateMemberLog(memberLogDTO));
    }

    @DeleteMapping("{id}")
    public R deleteMemberLog(@PathVariable String id){
        return R.ok(memberLogService.removeMemberLog(id));
    }

    /**
     * 按id查询详情信息
     * @param id  唯一标识
     * @param memberId 客户标识
     * @return
     */
    @GetMapping("list")
    public R selectMemberLogById(MemberLogDTO memberLogDTO){
        return R.ok(memberLogService.selectMemberLogVO(memberLogDTO));
    }

    /**
     * 按会员标识查询
     * @return
     */
//    @GetMapping("bymemberid/{memberId}")
//    public R selectMemberLogByMemberId(@PathVariable String memberId){
//        return R.ok(memberLogService.selectMemberLogVOByMemberId(memberId));
//    }

}
