package com.quotorcloud.quotor.academy.service.employee;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.dto.employee.EmployeeDTO;
import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.quotorcloud.quotor.academy.api.vo.Pager;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeePerformanceVO;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeVO;

import java.util.List;

/**
 * <p>
 * 员工信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-29
 */
public interface EmployeeService extends IService<Employee> {

    Boolean saveEmployee(EmployeeDTO employeeDTO);

    JSONObject listEmployee(EmployeeDTO employeeDTO);

    Boolean removeEmployee(String id);

    Boolean updateEmployee(EmployeeDTO employeeDTO);

    EmployeeVO selectEmployeeById(String id);

    JSONObject selectEmployeePositionAndHeadTitle();

    List<JSONObject> selectEmployeeListBox(String shopId);

    /**
     * 查询汇总业绩按分店分组
     */
    List<JSONObject> selectEmployeePerformanceGroupByShopId(EmployeeDTO employeeDTO);

    /**
     * 查询员工业绩
     * @param employeeDTO
     * @return
     */
    IPage<EmployeePerformanceVO> selectEmployeePerFormance(Page page, EmployeeDTO employeeDTO);

    /**
     * 汇总查询员工id
     */
    Pager selectEmployeePerformanceGroupById(Page page, EmployeeDTO employeeDTO);

    /**
     * 查询项目绩效、卡绩效详细信息
     */
    IPage selectEmployeePerformanceDetails(Page page, EmployeeDTO employeeDTO);

}
