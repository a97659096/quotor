package com.quotorcloud.quotor.academy.mapper.employee;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointEmployeeDTO;
import com.quotorcloud.quotor.academy.api.dto.employee.EmployeeDTO;
import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeePerformanceVO;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeVO;
import com.quotorcloud.quotor.academy.api.vo.user.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-29
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> selectEmployeePositionAndHeadTitle();

    List<AppointEmployeeDTO> selectAppointEmployee(@Param("shopId") String shopId);

//    List<EmployeeAttendanceStatisticsVO> selectAttendanceStatisrics(QueryWrapper<EmployeeAttendanceStatisticsVO> e_user_id);

    IPage<EmployeePerformanceVO> selectEmployeePerformance(Page page, @Param("emp") EmployeeDTO employeeDTO);

    List<EmployeePerformanceVO> selectEmployeePerformance(@Param("emp") EmployeeDTO employeeDTO);

    /**
     * 查询员工业绩、卡耗、服务会员数等信息根据员工分组
     * @param employeeDTO
     * @return
     */
    List<EmployeeVO> selectEmployeePerformanceAll(@Param("emp") EmployeeDTO employeeDTO);
    Page<EmployeeVO> selectEmployeePerformanceAll(Page page, @Param("emp") EmployeeDTO employeeDTO);

    UserVO selectUserInfoByUserId(@Param("userId") String userId);
}
