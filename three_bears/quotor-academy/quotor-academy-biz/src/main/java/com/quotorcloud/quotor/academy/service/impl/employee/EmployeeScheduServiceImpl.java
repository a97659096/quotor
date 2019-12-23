package com.quotorcloud.quotor.academy.service.impl.employee;

import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeSchedu;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeScheduVO;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeMapper;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeScheduMapper;
import com.quotorcloud.quotor.academy.service.employee.EmployeeScheduService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 员工排班信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
@Service
public class EmployeeScheduServiceImpl extends ServiceImpl<EmployeeScheduMapper, EmployeeSchedu> implements EmployeeScheduService {

    @Autowired
    private EmployeeScheduMapper employeeScheduMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Override
    public Boolean saveEmployeeSchedu(EmployeeSchedu employeeSchedu) {
        setEmployeeName(employeeSchedu);
        employeeScheduMapper.insert(employeeSchedu);
        return Boolean.TRUE;
    }

    private void setEmployeeName(EmployeeSchedu employeeSchedu) {
        shopSetterUtil.shopSetter(employeeSchedu, employeeSchedu.getEsShopId());
        //根据员工id查询到员工信息
        Employee employee = employeeMapper.selectById(employeeSchedu.getEsEmployeeId());
        //将员工姓名set进去
        employeeSchedu.setEsEmployeeName(employee.getName());
    }

    @Override
    public Boolean removeEmployeeSchedu(String id) {
        employeeScheduMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateEmployeeSchedu(EmployeeSchedu employeeSchedu) {
        setEmployeeName(employeeSchedu);
        employeeScheduMapper.updateById(employeeSchedu);
        return Boolean.TRUE;
    }

    @Override
    public List<EmployeeScheduVO> selectEmployeeSchedu(String shopId) {
        return employeeScheduMapper.selectEmployeeScheduVO(shopId);
    }
}
