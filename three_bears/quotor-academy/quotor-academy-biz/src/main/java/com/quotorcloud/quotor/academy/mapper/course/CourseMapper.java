package com.quotorcloud.quotor.academy.mapper.course;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.course.CourseDTO;
import com.quotorcloud.quotor.academy.api.entity.course.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 课程信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-28
 */
public interface CourseMapper extends BaseMapper<Course> {

    IPage<Course> selectCoursePage(Page coursePage, @Param("course") CourseDTO courseDTO);

}
