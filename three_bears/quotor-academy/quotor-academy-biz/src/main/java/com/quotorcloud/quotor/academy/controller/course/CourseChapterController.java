package com.quotorcloud.quotor.academy.controller.course;


import com.quotorcloud.quotor.academy.api.entity.course.CourseChapter;
import com.quotorcloud.quotor.academy.service.course.CourseChapterService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程章节 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-29
 */
@RestController
@RequestMapping("/course/chapter")
public class CourseChapterController {

    @Autowired
    private CourseChapterService courseChapterService;

    @PostMapping
    public R saveCourseChapter(@RequestBody CourseChapter courseChapter){
        return R.ok(courseChapterService.save(courseChapter));
    }

    @PutMapping
    public R updateCourseChapter(@RequestBody CourseChapter courseChapter){
        return R.ok(courseChapterService.updateById(courseChapter));
    }

    @DeleteMapping("{id}")
    public R deleteCourseChapter(@PathVariable String id){
        return R.ok(courseChapterService.removeById(id));
    }

}
