package com.quotorcloud.quotor.academy.service.employee;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeAttendanceVO;

import java.util.List;

/**
 * <p>
 * 员工考勤信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
public interface EmployeeAttendanceService extends IService<EmployeeAttendance> {

    //打卡
    Boolean checkWorkAttendance(String userId);

    IPage<EmployeeAttendanceVO> listCheckWorkAttendance(Page page, EmployeeAttendance employeeAttendance);

    Boolean updateWorkAttendanceStateNormal(String id);

    List<EmployeeAttendanceVO> listCheckWorkAttendanceByMonth(EmployeeAttendance employeeAttendance);

    void timerTaskAttendance();

    IPage statisticsAttendance(Page page, EmployeeAttendance employeeAttendance);
}
