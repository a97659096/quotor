package com.quotorcloud.quotor.academy.controller.course;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.course.CourseDTO;
import com.quotorcloud.quotor.academy.service.course.CourseService;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

/**
 * <p>
 * 课程信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-28
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 插入富文本圖片
     * @param textImg 图片
     * @return
     */
    @PostMapping("/text_img")
    public R saveRichText(MultipartFile textImg){
        return R.ok(courseService.saveCourseImg(textImg));
    }

    /**
     * 插入課程信息
     * @param courseDTO
     * @return
     */
    @PostMapping("save")
    public R saveCourse(CourseDTO courseDTO){
        QuotorUser quotorUser = SecurityUtils.getUser();
        return R.ok(courseService.saveCourse(quotorUser, courseDTO));
    }

    /**
     * 查詢課程信息
     * @param courseDTO
     * @return
     * @throws ParseException
     */
    @GetMapping("list")
    public R listCourse(CourseDTO courseDTO) throws ParseException {
        return R.ok(courseService.listCourse(courseDTO));
    }

    /**
     * 按id查询课程
     * @param id
     * @return
     * @throws ParseException
     */
    @GetMapping("list/{id}")
    public R selectCourseById(@PathVariable String id) throws ParseException {
        return R.ok(courseService.selectCourseById(id));
    }

    /**
     * 删除课程信息
     * @param id 唯一标识
     * @return
     */
    @DeleteMapping("/{id}")
    public R removeCourse(@PathVariable String id){
        QuotorUser quotorUser = SecurityUtils.getUser();
        return R.ok(courseService.removeCourse(quotorUser, id));
    }

    /**
     * 修改课程信息
     * @param courseDTO
     * @return
     */
    @PutMapping("update")
    public R updateCourse(CourseDTO courseDTO){
        QuotorUser quotorUser = SecurityUtils.getUser();
        return R.ok(courseService.updateCourse(quotorUser, courseDTO, courseDTO.getId()));
    }


    /**
     * 小程序查询课程列表
     * @param page
     * @param courseDTO
     * @return
     */
    @GetMapping("list/applet")
    public R listApplet(Page page, CourseDTO courseDTO){
        return R.ok(courseService.listCourseApplet(page, courseDTO));
    }

}
