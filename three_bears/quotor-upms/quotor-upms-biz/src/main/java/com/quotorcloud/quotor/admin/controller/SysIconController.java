package com.quotorcloud.quotor.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotorcloud.quotor.admin.api.dto.IconDTO;
import com.quotorcloud.quotor.admin.api.entity.SysIcon;
import com.quotorcloud.quotor.admin.service.SysIconService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 图标管理 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@RestController
@RequestMapping("/icon")
public class SysIconController {

    @Autowired
    private SysIconService sysIconService;

    @GetMapping("list")
    public R listIcon(IconDTO iconDTO){
        return R.ok(sysIconService.selectIcon(iconDTO));
    }

    @PostMapping("save")
    public R saveIcon(@RequestBody SysIcon sysIcon){
        return R.ok(sysIconService.saveIcon(sysIcon));
    }

    @PutMapping("update")
    public R updateIcon(@RequestBody SysIcon sysIcon){
        return R.ok(sysIconService.updateIcon(sysIcon));
    }

    @DeleteMapping("{id}")
    public R removeIcon(@PathVariable String id){
        return R.ok(sysIconService.removeIcon(id));
    }

    @GetMapping("list/{id}")
    public R selectIconById(@PathVariable String id){
        return R.ok(sysIconService.getOne(new QueryWrapper<SysIcon>().eq("id", id)));
    }

}
