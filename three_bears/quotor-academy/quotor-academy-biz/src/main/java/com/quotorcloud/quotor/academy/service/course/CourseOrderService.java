package com.quotorcloud.quotor.academy.service.course;

import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.api.dto.course.CourseOrderDTO;
import com.quotorcloud.quotor.academy.api.entity.course.CourseOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 课程订单表（课程预约） 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-05
 */
public interface CourseOrderService extends IService<CourseOrder> {

    String saveCourseOrder(String courseId, String name, String userId, String phone, HttpServletRequest request);

    Map<String, String> saveJSAPICourseOrder(CourseOrderDTO courseOrderDTO, HttpServletRequest request);

    JSONObject listAppointment(CourseOrderDTO courseOrderDTO);

    Boolean removeCourseOrder(String id);

    JSONObject listCourseOrder(CourseOrderDTO courseOrderDTO);
}
