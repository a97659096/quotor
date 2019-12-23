package com.quotorcloud.quotor.academy.controller.employee;


import com.quotorcloud.quotor.academy.api.dto.employee.EmployeeDTO;
import com.quotorcloud.quotor.academy.service.employee.EmployeeService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 按id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("list/{id}")
    public R selectEmployeeById(@PathVariable String id){
        return R.ok(employeeService.selectEmployeeById(id));
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping("save")
    public R saveEmployee(EmployeeDTO employeeDTO){
        return R.ok(employeeService.saveEmployee(employeeDTO));
    }

    /**
     * 查询员工列表
     * @param employeeDTO
     * @return
     */
    @GetMapping("list")
    public R listEmployee(EmployeeDTO employeeDTO){
        return R.ok(employeeService.listEmployee(employeeDTO));
    }

    /**
     * 删除员工信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteEmployee(@PathVariable String id){
        return R.ok(employeeService.removeEmployee(id));
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping("/update")
    public R updateEmployee(EmployeeDTO employeeDTO){
        return R.ok(employeeService.updateEmployee(employeeDTO));
    }

    /**
     * 查询店铺中员工的下拉列表
     * @param shopId
     * @return
     */
    @GetMapping("list/box")
    public R listBoxEmployee(String shopId){
        return R.ok(employeeService.selectEmployeeListBox(shopId));
    }

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    @GetMapping("userinfo")
    public R userInfo(String userId){
        return R.ok(employeeService.selectUserInfoByUserId(userId));
    }

    /**
     * 查询业绩列表按门店分组
     * @param employeeDTO
     * @return
     */
    @GetMapping("performance/shop")
    public R performanceShop(EmployeeDTO employeeDTO){
        return R.ok(employeeService.selectEmployeePerformanceGroupByShopId(employeeDTO));
    }

    /**
     * 查询员工业绩详情 按店铺
     */
    @GetMapping("performance/employee")
    public R performanceEmployee(EmployeeDTO employeeDTO){
        return R.ok(employeeService.selectEmployeePerFormance(employeeDTO));
    }
}
