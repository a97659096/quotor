package com.quotorcloud.quotor.academy.controller.employee;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendanceRule;
import com.quotorcloud.quotor.academy.service.employee.EmployeeAttendanceRuleService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工考勤规则信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/employee/attendance/rule")
public class EmployeeAttendanceRuleController {

    @Autowired
    private EmployeeAttendanceRuleService employeeAttendanceRuleService;

    /**
     * 新增考勤规则
     * @param employeeAttendanceRule
     * @return
     */
    @PostMapping
    public R saveEmployeeAttendanceRule(@RequestBody EmployeeAttendanceRule employeeAttendanceRule){
        return R.ok(employeeAttendanceRuleService.saveEmployeeAttendance(employeeAttendanceRule));
    }

    /**
     * 修改考勤规则
     * @param employeeAttendanceRule
     * @return
     */
    @PutMapping
    public R updateEmployeeAttendanceRule(@RequestBody EmployeeAttendanceRule employeeAttendanceRule){
        return R.ok(employeeAttendanceRuleService.updateEmployeeAttendance(employeeAttendanceRule));
    }

    /**
     * 删除考勤规则
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeEmployeeAttendanceRule(@PathVariable String id){
        return R.ok(employeeAttendanceRuleService.removeById(id));
    }

    /**
     * 分页查询考勤规则列表
     * @param page
     * @param attendanceRule
     * @return
     */
    @GetMapping("list")
    public R listEmployeeAttendanceRule(Page page, EmployeeAttendanceRule attendanceRule){
        return R.ok(employeeAttendanceRuleService.listEmployeeAttendanceRule(page, attendanceRule));
    }




}
