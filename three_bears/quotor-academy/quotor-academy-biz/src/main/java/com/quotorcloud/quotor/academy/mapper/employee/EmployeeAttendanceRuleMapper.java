package com.quotorcloud.quotor.academy.mapper.employee;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendanceRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeAttendanceRuleVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 员工考勤规则信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
public interface EmployeeAttendanceRuleMapper extends BaseMapper<EmployeeAttendanceRule> {

    IPage<EmployeeAttendanceRuleVO> listEmployeeAttendanceRule(Page<EmployeeAttendanceRuleVO> page, @Param("rule") EmployeeAttendanceRule employeeAttendanceRule);

}
