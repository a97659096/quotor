package com.quotorcloud.quotor.academy.service.impl.employee;

import com.quotorcloud.quotor.academy.api.dto.employee.EmployeePerformanceDTO;
import com.quotorcloud.quotor.academy.api.entity.employee.OrderDetailServiceStaff;
import com.quotorcloud.quotor.academy.mapper.employee.OrderDetailServiceStaffMapper;
import com.quotorcloud.quotor.academy.service.employee.OrderDetailServiceStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单服务人员 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Service
public class OrderDetailServiceStaffServiceImpl extends ServiceImpl<OrderDetailServiceStaffMapper, OrderDetailServiceStaff> implements OrderDetailServiceStaffService {

    @Autowired
    private OrderDetailServiceStaffMapper orderDetailServiceStaffMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    /**
     * 查询员工业绩信息
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param employeeId 员工标识
     * @return
     */
    @Override
    public List<OrderDetailServiceStaff> listEmployeePerformanceVO(EmployeePerformanceDTO employeePerformanceDTO) {
        //设置店铺信息
        shopSetterUtil.shopSetter(employeePerformanceDTO, employeePerformanceDTO.getShopId());
        //查询业绩/卡耗/提成信息并返回
        return orderDetailServiceStaffMapper.listEmployeePerformance(employeePerformanceDTO);
    }
}
