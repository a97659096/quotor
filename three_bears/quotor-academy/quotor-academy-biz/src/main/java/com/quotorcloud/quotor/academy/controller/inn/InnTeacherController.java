package com.quotorcloud.quotor.academy.controller.inn;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.inn.InnTeacherDTO;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacher;
import com.quotorcloud.quotor.academy.service.inn.InnTeacherService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 下店老师信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/inn/teacher")
public class InnTeacherController {

    @Autowired
    private InnTeacherService innTeacherService;

    @PostMapping
    public R saveInnTeacher(InnTeacherDTO innTeacherDTO){
        return R.ok(innTeacherService.saveInnTeacher(innTeacherDTO));
    }

    @GetMapping("list")
    public R listInnTeacher(Page<InnTeacher> page, InnTeacherDTO innTeacherDTO){
        return R.ok(innTeacherService.listInnTeacher(page, innTeacherDTO));
    }

    @GetMapping("list/{id}")
    public R listInnTeacherById(@PathVariable String id){
        return R.ok(innTeacherService.selectInnTeacherById(id));
    }

    @PutMapping
    public R updateInnTeacher(InnTeacherDTO innTeacherDTO){
        return R.ok(innTeacherService.updateInnTeacher(innTeacherDTO));
    }

    @DeleteMapping("{id}")
    public R deleteInnTeacher(@PathVariable String id){
        return R.ok(innTeacherService.removeInnTeacher(id));
    }

    @GetMapping("listbox")
    public R listBoxInnTeacher(){
        return R.ok(innTeacherService.list());
    }
}
