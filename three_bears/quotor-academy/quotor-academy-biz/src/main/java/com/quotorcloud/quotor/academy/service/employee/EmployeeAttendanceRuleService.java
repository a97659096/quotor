package com.quotorcloud.quotor.academy.service.employee;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendanceRule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeAttendanceRuleVO;

/**
 * <p>
 * 员工考勤规则信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
public interface EmployeeAttendanceRuleService extends IService<EmployeeAttendanceRule> {

    Boolean saveEmployeeAttendance(EmployeeAttendanceRule employeeAttendanceRule);

    IPage<EmployeeAttendanceRuleVO> listEmployeeAttendanceRule(Page<EmployeeAttendanceRuleVO> page, EmployeeAttendanceRule employeeAttendanceRule);

    Boolean updateEmployeeAttendance(EmployeeAttendanceRule employeeAttendanceRule);
}
