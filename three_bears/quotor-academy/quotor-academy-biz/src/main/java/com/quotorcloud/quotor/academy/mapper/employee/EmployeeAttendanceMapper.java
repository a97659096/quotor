package com.quotorcloud.quotor.academy.mapper.employee;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeAttendanceStatisticsVO;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeAttendanceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工考勤信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
public interface EmployeeAttendanceMapper extends BaseMapper<EmployeeAttendance> {

    IPage<EmployeeAttendanceVO> listCheckWorkAttendance(Page page,@Param("attendance") EmployeeAttendance employeeAttendance);

    List<EmployeeAttendanceVO> listCheckWorkAttendance(@Param("attendance") EmployeeAttendance employeeAttendance);

    IPage<EmployeeAttendanceStatisticsVO> selectAttendanceStatistics(Page page, @Param("attendance") EmployeeAttendance employeeAttendance);
}
