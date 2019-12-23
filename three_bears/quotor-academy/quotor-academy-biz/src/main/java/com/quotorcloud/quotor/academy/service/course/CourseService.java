package com.quotorcloud.quotor.academy.service.course;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.dto.course.CourseDTO;
import com.quotorcloud.quotor.academy.api.entity.course.Course;
import com.quotorcloud.quotor.academy.api.vo.course.CourseVO;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 课程信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-28
 */
public interface CourseService extends IService<Course> {

    Boolean saveCourse(QuotorUser quotorUser, CourseDTO courseDTO);

    String saveCourseImg(MultipartFile multipartFile);

    JSONObject listCourse(CourseDTO courseDTO) throws ParseException;

    Boolean removeCourse(QuotorUser quotorUser, String id);

    Boolean updateCourse(QuotorUser quotorUser, CourseDTO courseDTO, String id);

    CourseVO selectCourseById(String id) throws ParseException;

    //查询课程列表
    List<JSONObject> listCourseListBox(Integer type);

    List<JSONObject> listCourseApplet(Page page, CourseDTO courseDTO);

}
