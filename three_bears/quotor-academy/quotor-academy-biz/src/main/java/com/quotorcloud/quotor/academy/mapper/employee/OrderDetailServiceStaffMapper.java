package com.quotorcloud.quotor.academy.mapper.employee;

import com.quotorcloud.quotor.academy.api.dto.employee.EmployeePerformanceDTO;
import com.quotorcloud.quotor.academy.api.entity.employee.OrderDetailServiceStaff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单服务人员 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface OrderDetailServiceStaffMapper extends BaseMapper<OrderDetailServiceStaff> {

    List<OrderDetailServiceStaff> listEmployeePerformance(@Param("performance") EmployeePerformanceDTO employeePerformanceDTO);

    List<OrderDetailServiceStaff> listEmployeePerformanceDetails(@Param("performance") EmployeePerformanceDTO employeePerformanceDTO);
}
