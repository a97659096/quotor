package com.quotorcloud.quotor.academy.mapper.course;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.course.TeacherDTO;
import com.quotorcloud.quotor.academy.api.entity.teacher.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 讲师信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-24
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    IPage<Teacher> selectTeacherPage(Page<Teacher> page,@Param("teacher") TeacherDTO teacherDTO);

    List<Teacher> selectTeacherNation();
}
