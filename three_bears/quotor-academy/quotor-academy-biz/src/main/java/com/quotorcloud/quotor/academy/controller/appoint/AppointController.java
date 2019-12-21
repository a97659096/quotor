package com.quotorcloud.quotor.academy.controller.appoint;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointDTO;
import com.quotorcloud.quotor.academy.api.vo.appoint.AppointVO;
import com.quotorcloud.quotor.academy.service.appoint.AppointService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预约信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-21
 */
@RestController
@RequestMapping("/appoint")
public class AppointController {

    @Autowired
    private AppointService appointService;

    /**
     * 新增预约记录
     * @param appointDTO
     * @return
     */
    @PostMapping
    public R saveAppoint(@RequestBody AppointDTO appointDTO){
        return R.ok(appointService.saveAppoint(appointDTO));
    }

    /**
     * 修改预约记录
     * @param appointDTO
     * @return
     */
    @PutMapping
    public R updateAppoint(@RequestBody AppointDTO appointDTO){
        return R.ok(appointService.updateAppoint(appointDTO));
    }

    /**
     * 删除预约记录
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R cancelAppoint(@PathVariable String id){
        return R.ok(appointService.cancelAppoint(id));
    }

    /**
     * 表格形式展示
     * @param appointVO
     * @return
     */
    @GetMapping("form")
    public R formAppoint(AppointVO appointVO){
        return R.ok(appointService.formAppoint(appointVO));
    }

    /**
     * 列表形式展示
     * @param page
     * @param appointVO
     * @return
     */
    @GetMapping("list")
    public R listAppoint(Page<AppointVO> page, AppointVO appointVO){
        return R.ok(appointService.listAppoint(page, appointVO));
    }

    /**
     * 查询单个预约记录
     * @param id
     * @param appointId
     * @return
     */
    @GetMapping("getone")
    public R getOneAppoint(String id, String appointId){
        return R.ok(appointService.listAppointById(id, appointId));
    }

}
