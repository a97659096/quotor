package com.quotorcloud.quotor.academy.controller.course;


import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.api.dto.course.CourseOrderDTO;
import com.quotorcloud.quotor.academy.api.entity.course.CourseOrder;
import com.quotorcloud.quotor.academy.service.course.CourseOrderService;
import com.quotorcloud.quotor.academy.service.course.CourseService;
import com.quotorcloud.quotor.academy.service.course.TeacherService;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程订单表（课程预约） 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-05
 */
@RestController
@RequestMapping("/course/order")
public class CourseOrderController {

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OrderUtil orderUtil;

    //保存订单生成二维码返回，native扫码支付
    @PostMapping("/native/save")
    public void saveCourseOrder(CourseOrderDTO courseOrderDTO,
                                HttpServletRequest request, HttpServletResponse response){

        String codeUrl = courseOrderService.saveCourseOrder(courseOrderDTO, request);

        if(codeUrl == null) {
            throw new  NullPointerException();
        }

        orderUtil.genertorQRCode(codeUrl, response);

    }

    @GetMapping("/jsapi/save")
    public R saveJSAPICourseOrder(String courseId, Integer userId, String code,
                                  HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    @GetMapping("/list/appointment")
    public R listAppointment(CourseOrderDTO courseOrderDTO){
        return R.ok(courseOrderService.listAppointment(courseOrderDTO));
    }

    @GetMapping("/listbox")
    public R listBox(Integer status){
        List<JSONObject> courseListbox = courseService.listCourseListBox(status);
        List<JSONObject> teacherListbox = teacherService.listBoxTeacher();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseListbox", courseListbox);
        jsonObject.put("teacherListbox", teacherListbox);
        return R.ok(jsonObject);
    }

    @PostMapping
    public R saveCourseOrder(@RequestBody CourseOrder courseOrder){
        return R.ok(courseOrderService.save(courseOrder));
    }

    /**
     * 批量修改学习状态
     * @param courseOrders
     * @return
     */
    @PutMapping("study-state")
    public R updateBatchStudyState(@RequestBody List<CourseOrder> courseOrders){
        return R.ok(courseOrderService.updateBatchById(courseOrders));
    }

    @GetMapping("list")
    public R listOrder(CourseOrderDTO courseOrderDTO){
        return R.ok(courseOrderService.listCourseOrder(courseOrderDTO));
    }



}
