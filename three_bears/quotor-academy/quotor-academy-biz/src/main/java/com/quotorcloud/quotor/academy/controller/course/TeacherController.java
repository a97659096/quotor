package com.quotorcloud.quotor.academy.controller.course;

import com.quotorcloud.quotor.academy.api.dto.course.TeacherDTO;
import com.quotorcloud.quotor.academy.service.course.TeacherService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-24
 */
@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 新增讲师
     * @param teacherDTO 讲师实体类
     * @return
     */
    @PostMapping("save")
    public R saveTeacher(TeacherDTO teacherDTO){
        return R.ok(teacherService.saveTeacher(teacherDTO));
    }

    /**
     * 查询讲师列表
     * @param pageNo 第几页
     * @param pageSize 显示条数
     * @param name 讲师姓名
     * @param nationality 讲师国籍
     * @param phone 电话
     * @param jobState 工作状态
     * @return
     */
    @GetMapping("list")
    public R listTeacher(TeacherDTO teacherDTO){
        return R.ok(teacherService.listTeacher(teacherDTO));
    }

    /**
     * 修改讲师信息
     * @param teacherDTO
     * @return
     */
    @PutMapping("update")
    public R updateTeacher(TeacherDTO teacherDTO){
        return R.ok(teacherService.updateTeacher(teacherDTO));
    }

    /**
     * 删除讲师信息（逻辑删除）
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id){
        return R.ok(teacherService.deleteTeacher(id));
    }

    /**
     * 讲师下拉框查询
     * @return
     */
    @GetMapping("/list/box")
    public R listBoxTeacher(){
        return R.ok(teacherService.listBoxTeacher());
    }

    /**
     * 按id查询讲师信息
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    public R selectTeacherById(@PathVariable String id){
        return R.ok(teacherService.selectTeacherById(id));
    }

    /**
     * 查询讲师国籍列表
     * @return
     */
    @GetMapping("/nation")
    public R selectTeacherNation(){
        return R.ok(teacherService.selectTeacherNation());
    }

}
