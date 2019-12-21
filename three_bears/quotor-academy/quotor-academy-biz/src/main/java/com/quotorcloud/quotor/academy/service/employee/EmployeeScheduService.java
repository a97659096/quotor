package com.quotorcloud.quotor.academy.service.employee;

import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeSchedu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeScheduVO;

import java.util.List;

/**
 * <p>
 * 员工排班信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
public interface EmployeeScheduService extends IService<EmployeeSchedu> {

    Boolean saveEmployeeSchedu(EmployeeSchedu employeeSchedu);

    Boolean removeEmployeeSchedu(String id);

    Boolean updateEmployeeSchedu(EmployeeSchedu employeeSchedu);

    List<EmployeeScheduVO> selectEmployeeSchedu(String shopId);
}
