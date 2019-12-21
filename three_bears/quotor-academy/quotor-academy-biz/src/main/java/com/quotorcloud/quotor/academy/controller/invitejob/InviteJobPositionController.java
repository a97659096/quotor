package com.quotorcloud.quotor.academy.controller.invitejob;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.invitejob.InviteJobPosition;
import com.quotorcloud.quotor.academy.service.invitejob.InviteJobPositionService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 招聘岗位信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/invite/job/position")
public class InviteJobPositionController {

    @Autowired
    private InviteJobPositionService inviteJobPositionService;

    /**
     * 新增职位信息
     * @param inviteJobPosition
     * @return
     */
    @PostMapping
    public R savePosition(@RequestBody InviteJobPosition inviteJobPosition){
        return R.ok(inviteJobPositionService.save(inviteJobPosition));
    }

    /**
     * 分页查询职位信息列表
     * @param page
     * @param inviteJobPosition
     * @return
     */
    @GetMapping("list")
    public R listPosition(Page<InviteJobPosition> page, InviteJobPosition inviteJobPosition){
        return R.ok(inviteJobPositionService.page(page, new QueryWrapper<>()));
    }

    /**
     * 查询职位下拉列表，不分页
     * @return
     */
    @GetMapping("list/box")
    public R listBoxPosition(){
        return R.ok(inviteJobPositionService.list(new QueryWrapper<>()));
    }

    /**
     * 删除求职/招聘职位信息
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removePosition(@PathVariable String id){
        return R.ok(inviteJobPositionService.removeById(id));
    }

    /**
     * 修改求职/招聘信息
     * @param inviteJobPosition
     * @return
     */
    @PutMapping
    public R updatePosition(@RequestBody InviteJobPosition inviteJobPosition){
        return R.ok(inviteJobPositionService.updateById(inviteJobPosition));
    }
}
