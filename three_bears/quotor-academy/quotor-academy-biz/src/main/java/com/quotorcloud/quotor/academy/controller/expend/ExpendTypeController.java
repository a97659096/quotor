package com.quotorcloud.quotor.academy.controller.expend;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quotorcloud.quotor.academy.service.employee.EmployeeService;
import com.quotorcloud.quotor.academy.service.expend.ExpendTypeService;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.core.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/expend-type")
public class ExpendTypeController {

    @Autowired
    private ExpendTypeService expendTypeService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * pc端支出类型下拉列表
     * @param shopId
     * @return
     */
    @GetMapping
    public R listExpendType(String shopId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("expendType", expendTypeService.list(new QueryWrapper<>()));
        jsonObject.put("employee", employeeService.selectEmployeeListBox(shopId));
        return R.ok(jsonObject);
    }



}
