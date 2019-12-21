package com.quotorcloud.quotor.academy.controller.employee;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendance;
import com.quotorcloud.quotor.academy.service.employee.EmployeeAttendanceService;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>
 * 员工考勤信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/employee/attendance")
public class EmployeeAttendanceController {

    @Autowired
    private EmployeeAttendanceService employeeAttendanceService;

    /**
     * 过期秒数
     */
    @Value("${attendance.expireSecond}")
    private static Integer expireSecond;

    /**
     * 扫码地址
     */
    @Value("${attendance.qrurl}")
    private static String ATTENDANCE_QR;

    @Autowired
    private OrderUtil orderUtil;

    /**
     * 生成考勤二维码 地址加时间戳
     * @param timestamp
     * @param response
     */
    @GetMapping("getqr")
    public void getAttendanceQr(HttpServletResponse response){
        orderUtil.genertorQRCode(ATTENDANCE_QR + "?timestamp=" + System.currentTimeMillis() , response);
    }

    /**
     * 打卡
     * @param userId 用户标识
     * @param timestamp 用来判断二维码是否过期
     * @return
     */
    @GetMapping("check-work")
    public R checkWorkAttendance(String userId, long timestamp){
        //判断二维码是否过期
        long now = System.currentTimeMillis();
        if((now - timestamp) / 1000 > expireSecond){
            return R.failed("二维码链接失效，请重新获取二维码");
        }
        return R.ok(employeeAttendanceService.checkWorkAttendance(userId));
    }

    /**
     * 将考勤状态改为正常
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public R updateWorkAttendanceStateNormal(@PathVariable String id){
        return R.ok(employeeAttendanceService.updateWorkAttendanceStateNormal(id));
    }

    /**
     * 将当天的考勤状况查询出来
     * @param page
     * @param attendanceState 考勤状态
     * @param 店铺标识
     * @param date 日期
     * @return
     */
    @GetMapping("list")
    public R listCheckWorkAttendance(Page page, EmployeeAttendance employeeAttendance){
        return R.ok(employeeAttendanceService.listCheckWorkAttendance(page, employeeAttendance));
    }

    /**
     * 查询指定月份的考勤状态
     * @param dateMonth  2019-11 指定月份
     * @return
     */
    @GetMapping("list/month")
    public R listCheckWorkAttendanceByMonth(EmployeeAttendance employeeAttendance){
        return R.ok(employeeAttendanceService.listCheckWorkAttendanceByMonth(employeeAttendance));
    }

    /**
     * 汇总查询
     * @param page
     * @param dateRange 1111,2222 开始-结束时间
     * @param shopId  店铺标识
     * @return
     */
    @GetMapping("list/statistic")
    public R statisticsAttendance(Page page, EmployeeAttendance employeeAttendance){
        return R.ok(employeeAttendanceService.statisticsAttendance(page, employeeAttendance));
    }


}
