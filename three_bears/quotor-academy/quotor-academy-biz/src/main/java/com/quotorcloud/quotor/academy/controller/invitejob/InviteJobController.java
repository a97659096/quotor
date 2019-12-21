package com.quotorcloud.quotor.academy.controller.invitejob;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.invitejob.InviteJobDTO;
import com.quotorcloud.quotor.academy.api.vo.invitejob.InviteJobVO;
import com.quotorcloud.quotor.academy.service.invitejob.InviteJobService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * <p>
 * 招聘求职信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-04
 */
@RestController
@RequestMapping("/invite/job")
public class InviteJobController {

    @Autowired
    private InviteJobService inviteJobService;

    /**
     * 新增求职/招聘信息
     * @param inviteJobDTO
     * @return
     */
    @PostMapping
    public R saveInviteJob(InviteJobDTO inviteJobDTO){
        return R.ok(inviteJobService.saveInviteJob(inviteJobDTO));
    }

    /**
     * 修改求职/招聘信息
     * @param inviteJobDTO
     * @return
     */
    @PutMapping
    public R updateInviteJob(InviteJobDTO inviteJobDTO){
        return R.ok(inviteJobService.updateInviteJob(inviteJobDTO));
    }

    /**
     * 分页查询求职和招聘信息
     * @param page
     * @param inviteJobDTO
     * @return
     * @throws ParseException
     */
    @GetMapping("list")
    public R listInviteJob(Page<InviteJobVO> page, InviteJobDTO inviteJobDTO) throws ParseException {
        return R.ok(inviteJobService.listInviteJob(page, inviteJobDTO));
    }

    /**
     * 删除求职和招聘信息
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeInviteJob(@PathVariable String id){
        return R.ok(inviteJobService.removeInviteJob(id));
    }

    /**
     * 查询单个记录详情信息
     * @param type
     * @param id
     * @return
     * @throws ParseException
     */
    @GetMapping("list/one")
    public R selectInviteJobById(Integer type, String id) throws ParseException {
        return R.ok(inviteJobService.selectInviteJobById(type, id));
    }

}
