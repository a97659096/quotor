package com.quotorcloud.quotor.academy.service.impl.employee;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeScheduClass;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeScheduClassVO;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeScheduClassMapper;
import com.quotorcloud.quotor.academy.service.employee.EmployeeScheduClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.PageUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author tianshihao
 * @since 2019-11-19
 */
@Service
public class EmployeeScheduClassServiceImpl extends ServiceImpl<EmployeeScheduClassMapper, EmployeeScheduClass> implements EmployeeScheduClassService {

    @Autowired
    private EmployeeScheduClassMapper employeeScheduClassMapper;

    @Override
    public Boolean saveEmployeeScheduClass(EmployeeScheduClass employeeScheduClass) {
        employeeScheduClass.setDelState(CommonConstants.STATUS_NORMAL);
        if (setDOValue(employeeScheduClass)) return null;
        employeeScheduClassMapper.insert(employeeScheduClass);
        return Boolean.TRUE;
    }

    private boolean setDOValue(EmployeeScheduClass employeeScheduClass) {
        //获取用户信息并赋值
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user) || ComUtil.isEmpty(user.getDeptId())){
            return true;
        }
        employeeScheduClass.setCreaterId(String.valueOf(user.getId()));
        employeeScheduClass.setCreaterName(user.getName());
        //判断部门id是否为空
        if(ComUtil.isEmpty(employeeScheduClass.getShopId())){
            employeeScheduClass.setShopId(String.valueOf(user.getDeptId()));
        }else {
            employeeScheduClass.setShopId(employeeScheduClass.getShopId());
        }
        return false;
    }

    @Override
    public Boolean removeEmployeeScheduClass(String id) {
        employeeScheduClassMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public JSONObject listClass(Page<EmployeeScheduClass> page, EmployeeScheduClass employeeScheduClass) {
        IPage<EmployeeScheduClass> employeeScheduClassIPage =
                employeeScheduClassMapper.selectPage(page, new QueryWrapper<>());

        List<EmployeeScheduClass> records = employeeScheduClassIPage.getRecords();

        List<EmployeeScheduClassVO> employeeScheduClassVOS = records.stream().map(this::getEmployeeScheduClassVO)
                .collect(Collectors.toList());

        return PageUtil.getPagePackage("classes", employeeScheduClassVOS, page);
    }

    private EmployeeScheduClassVO getEmployeeScheduClassVO(EmployeeScheduClass record) {
        EmployeeScheduClassVO employeeScheduClassVO = new EmployeeScheduClassVO();
        BeanUtils.copyProperties(record, employeeScheduClassVO, "workTimeScope");
        String workTimeScope = record.getWorkTimeScope();
        employeeScheduClassVO.setWorkTimeScope(Splitter.on(CommonConstants.SEPARATOR).splitToList(workTimeScope));
        return employeeScheduClassVO;
    }

    @Override
    public Boolean updateEmployeeScheduClass(EmployeeScheduClass employeeScheduClass) {
        if(setDOValue(employeeScheduClass)) return null;
        employeeScheduClassMapper.updateById(employeeScheduClass);
        return Boolean.TRUE;
    }

    @Override
    public EmployeeScheduClassVO getClassById(String id) {
        EmployeeScheduClass employeeScheduClass = this.getById(id);
        return getEmployeeScheduClassVO(employeeScheduClass);
    }
}
