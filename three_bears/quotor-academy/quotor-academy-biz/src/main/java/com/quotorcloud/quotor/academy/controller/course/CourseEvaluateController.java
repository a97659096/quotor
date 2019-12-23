package com.quotorcloud.quotor.academy.controller.course;


import com.quotorcloud.quotor.academy.api.entity.course.CourseEvaluate;
import com.quotorcloud.quotor.academy.service.course.CourseEvaluateService;
import com.quotorcloud.quotor.academy.service.course.CourseService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程评价 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-22
 */
@RestController
@RequestMapping("/course-evaluate")
public class CourseEvaluateController {

    @Autowired
    private CourseEvaluateService courseEvaluateService;

    @PostMapping
    public R saveCourseEvaluate(@RequestBody CourseEvaluate courseEvaluate){
        return R.ok(courseEvaluateService.save(courseEvaluate));
    }
}
