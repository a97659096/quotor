package com.quotorcloud.quotor.academy.service.course;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.entity.teacher.Teacher;
import com.quotorcloud.quotor.academy.api.dto.course.TeacherDTO;
import com.quotorcloud.quotor.academy.api.vo.teacher.TeacherVO;
import com.quotorcloud.quotor.common.security.service.QuotorUser;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 讲师信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-24
 */
public interface TeacherService extends IService<Teacher> {

    Boolean saveTeacher(QuotorUser user, TeacherDTO teacherDTO);

    JSONObject listTeacher(TeacherDTO teacherDTO);

    Boolean updateTeacher(QuotorUser user, TeacherDTO teacherDTO, String id);

    Boolean deleteTeacher(QuotorUser user, String id);

    List<JSONObject> listBoxTeacher();

    TeacherVO selectTeacherById(String id);

    Set<String> selectTeacherNation();

}
