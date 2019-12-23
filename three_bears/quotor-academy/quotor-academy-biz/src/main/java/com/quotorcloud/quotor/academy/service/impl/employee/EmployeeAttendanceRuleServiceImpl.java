package com.quotorcloud.quotor.academy.service.impl.employee;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendanceRule;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeAttendanceRuleVO;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeAttendanceRuleMapper;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeMapper;
import com.quotorcloud.quotor.academy.service.employee.EmployeeAttendanceRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工考勤规则信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
@Service
public class EmployeeAttendanceRuleServiceImpl extends ServiceImpl<EmployeeAttendanceRuleMapper, EmployeeAttendanceRule> implements EmployeeAttendanceRuleService {

    @Autowired
    private EmployeeAttendanceRuleMapper employeeAttendanceRuleMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Override
    public Boolean saveEmployeeAttendance(EmployeeAttendanceRule employeeAttendanceRule) {
        //获取用户
        shopSetterUtil.shopSetter(employeeAttendanceRule, employeeAttendanceRule.getShopId());
        //获取该店铺数据库中已经设有规则的员工id集合
        List<String> oldUserIdList = getOldUserList(employeeAttendanceRule.getShopId(), null);

        judgeEmpUserIdIsExist(employeeAttendanceRule, oldUserIdList);

        //设置创建人信息
        QuotorUser user = SecurityUtils.getUser();
        employeeAttendanceRule.setCreaterId(String.valueOf(user.getId()));
        employeeAttendanceRule.setCreaterName(user.getName());

        employeeAttendanceRuleMapper.insert(employeeAttendanceRule);
        return Boolean.TRUE;
    }


    @Override
    public Boolean updateEmployeeAttendance(EmployeeAttendanceRule employeeAttendanceRule) {
        //获取用户
        shopSetterUtil.shopSetter(employeeAttendanceRule, employeeAttendanceRule.getShopId());

        //获取该店铺数据库中已经设有规则的员工id集合
        List<String> oldUserIdList = getOldUserList(employeeAttendanceRule.getShopId(), employeeAttendanceRule.getId());
        judgeEmpUserIdIsExist(employeeAttendanceRule, oldUserIdList);

        employeeAttendanceRuleMapper.updateById(employeeAttendanceRule);
        return Boolean.TRUE;
    }


    /**
     * 查询员工排班规则列表
     * @param page
     * @param employeeAttendanceRule
     * @return
     */
    @Override
    public IPage<EmployeeAttendanceRuleVO> listEmployeeAttendanceRule(Page<EmployeeAttendanceRuleVO> page, EmployeeAttendanceRule employeeAttendanceRule) {
        IPage<EmployeeAttendanceRuleVO> employeeAttendanceRuleVOIPage = employeeAttendanceRuleMapper.listEmployeeAttendanceRule(page, employeeAttendanceRule);
        List<EmployeeAttendanceRuleVO> records = employeeAttendanceRuleVOIPage.getRecords();
        if(ComUtil.isEmpty(records)){
            return null;
        }
        //设置店铺信息
        shopSetterUtil.shopSetter(employeeAttendanceRule,employeeAttendanceRule.getShopId());
        String shopId = employeeAttendanceRule.getShopId();
        //根据用户获取店铺标识把员工列表查出来，方便后续赋值,此处查询出所有员工，防止之前已经新增的规则中有员工离职。
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>()
                .eq("e_del_state", CommonConstants.STATUS_NORMAL)
                .eq("e_shop_id", shopId));
        //将员工的userid和姓名对应上，方便后边直接获取
        Map<String, String> empMap = employees.stream().collect(Collectors.toMap(Employee::getUserId, Employee::getName));

        for (EmployeeAttendanceRuleVO employeeAttendanceRuleVO : records){
            String workday = employeeAttendanceRuleVO.getWorkday();
            if(!ComUtil.isEmpty(workday)){
                //根据1,2,3,4返回周一，周二，周三，周四,并重新赋值
                employeeAttendanceRuleVO.setWorkday(getWorkdayStringList(workday));
            }
            //获取出员工用户标识
            String empUserIdList = employeeAttendanceRuleVO.getEmpUserIdList();
            //判断是否为空
            if(!ComUtil.isEmpty(empUserIdList)){
                //获取出员工的userid集合然后遍历把名字获取到在进行赋值，暂不考虑后续离职问题
                List<String> empList = Splitter.on(CommonConstants.SEPARATOR).splitToList(empUserIdList);
                List<String> empNameList = empList.stream().map(empMap::get).collect(Collectors.toList());
                employeeAttendanceRuleVO.setEmpUserIdList(Joiner.on(CommonConstants.SEPARATOR).join(empNameList));
            }
        }
        return employeeAttendanceRuleVOIPage;
    }


    //获取数据库里含有的规则中的员工集合
    private List<String> getOldUserList(String deptId, String ruleId){
        List<EmployeeAttendanceRule> employeeAttendanceRules = employeeAttendanceRuleMapper
                .selectList(new QueryWrapper<EmployeeAttendanceRule>()
                        .eq("ear_shop_id", deptId).eq("ear_del_state", CommonConstants.STATUS_NORMAL)
        //如果ruleId等于空是新增，不等于空是修改，修改时判断是否已经存在时把当前修改的规则记录排除在外
                        .notIn(!ComUtil.isEmpty(ruleId),"ear_id", ruleId));
        //如果已有全店通用的情况下，直接抛出异常
        for (EmployeeAttendanceRule employeeAttendanceRule : employeeAttendanceRules){
            if(employeeAttendanceRule.getApplyEmp() == 1){
                throw new MyException("所选员工已有考勤规则，请前往处理后重新操作");
            }
        }
        if(!ComUtil.isEmpty(employeeAttendanceRules)){
            //把所有规则的员工信息放到一起
            List<String> allRuleUserIdList = employeeAttendanceRules.stream().map(EmployeeAttendanceRule::getEmpUserIdList).collect(Collectors.toList());
            //再用逗号隔开后，再放入到一个集合里，此集合为所有有规则员工的用户标识集合
            List<String> oldUserIdList = new ArrayList<>();
            allRuleUserIdList.forEach(ruleUserId -> oldUserIdList.addAll(Splitter.on(CommonConstants.SEPARATOR).splitToList(ruleUserId)));
            return oldUserIdList;
        }
        return null;
    }

    //判断员工userId是否已经含有
    private void judgeEmpUserIdIsExist(EmployeeAttendanceRule employeeAttendanceRule, List<String> oldUserIdList) {
        //如果新增的规则是对所有员工通用，那则去查询员工信息并赋值
        if(employeeAttendanceRule.getApplyEmp() == 1){
            //查询员工信息,只加入在职状态的员工
            List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>().eq("e_del_state", CommonConstants.STATUS_NORMAL)
                    .eq("e_work_state", CommonConstants.STATUS_IN_JOB).eq("e_shop_id", employeeAttendanceRule.getShopId()));
            //把员工的账号id加入集合
            List<String> empUserIdList = employees.stream().map(Employee::getUserId).collect(Collectors.toList());
            //判断两个集合中是否有相等的值
            disCollection(oldUserIdList, empUserIdList);
            //再用逗号分隔成字符串进行赋值操作
            employeeAttendanceRule.setEmpUserIdList(Joiner.on(CommonConstants.SEPARATOR).join(empUserIdList));
        }else {
            //把新加进来的员工也设置为一个集合
            List<String> newUserIdList = Splitter.on(CommonConstants.SEPARATOR).splitToList(employeeAttendanceRule.getEmpUserIdList());
            //判断是否有相同的元素，为false的时候有
            disCollection(oldUserIdList, newUserIdList);
        }
    }

    //判断两个集合中是否有相等的值
    private void disCollection(List<String> oldUserIdList, List<String> empUserIdList) {
        //判断是否有相同的元素，为false的时候有
        if (!ComUtil.isEmpty(oldUserIdList)) {
            boolean disjoint = Collections.disjoint(oldUserIdList, empUserIdList);
            //抛出异常
            if (!disjoint) {
                throw new MyException("所选员工已有考勤规则，请前往处理后重新操作");
            }
        }
    }


    //根据数字1,2,3返回周一，周二，周三
    private String getWorkdayStringList(String workday) {
        List<String> workdayList = Splitter.on(CommonConstants.SEPARATOR).splitToList(workday);
        List<String> collect = workdayList.stream().map(workdayInteger -> {
            String workdayChinese;
            switch (Integer.valueOf(workdayInteger)) {
                case 1:
                    workdayChinese = "周一";
                    break;
                case 2:
                    workdayChinese = "周二";
                    break;
                case 3:
                    workdayChinese = "周三";
                    break;
                case 4:
                    workdayChinese = "周四";
                    break;
                case 5:
                    workdayChinese = "周五";
                    break;
                case 6:
                    workdayChinese = "周六";
                    break;
                case 7:
                    workdayChinese = "周日";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Integer.valueOf(workdayInteger));
            }
            return workdayChinese;
        }).collect(Collectors.toList());
        return Joiner.on(CommonConstants.SEPARATOR).join(collect);
    }
}
