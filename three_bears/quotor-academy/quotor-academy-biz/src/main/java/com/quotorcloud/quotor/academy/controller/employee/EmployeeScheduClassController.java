package com.quotorcloud.quotor.academy.controller.employee;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeScheduClass;
import com.quotorcloud.quotor.academy.service.employee.EmployeeScheduClassService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工排班班次信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/employee/schedu/class")
public class EmployeeScheduClassController {

    @Autowired
    private EmployeeScheduClassService employeeScheduClassService;

    @PostMapping
    public R saveEmployeeScheduClass(@RequestBody EmployeeScheduClass employeeScheduClass){
        return R.ok(employeeScheduClassService.saveEmployeeScheduClass(employeeScheduClass));
    }

    @DeleteMapping("{id}")
    public R deleteEmployeeScheduClass(@PathVariable String id){
        return R.ok(employeeScheduClassService.removeEmployeeScheduClass(id));
    }

    @PutMapping
    public R updateEmployeeScheduClass(@RequestBody EmployeeScheduClass employeeScheduClass){
        return R.ok(employeeScheduClassService.updateEmployeeScheduClass(employeeScheduClass));
    }

    @GetMapping("list")
    public R listEmployeeScheduClass(Page<EmployeeScheduClass> page, EmployeeScheduClass employeeScheduClass){
        return R.ok(employeeScheduClassService.listClass(page, employeeScheduClass));
    }

    @GetMapping("list/{id}")
    public R getEmployeeScheduClassById(@PathVariable String id){
        return R.ok(employeeScheduClassService.getClassById(id));
    }

}
