package com.quotorcloud.quotor.academy.service.employee;

import com.quotorcloud.quotor.academy.api.dto.employee.EmployeePerformanceDTO;
import com.quotorcloud.quotor.academy.api.entity.employee.OrderDetailServiceStaff;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单服务人员 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface OrderDetailServiceStaffService extends IService<OrderDetailServiceStaff> {

    /**
     * 查询员工绩效
     * @param employeePerformanceDTO
     * @return
     */
    List<OrderDetailServiceStaff> listEmployeePerformanceVO(EmployeePerformanceDTO employeePerformanceDTO);


}
