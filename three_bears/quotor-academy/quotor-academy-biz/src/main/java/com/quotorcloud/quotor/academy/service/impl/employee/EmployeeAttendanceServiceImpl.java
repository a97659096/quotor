package com.quotorcloud.quotor.academy.service.impl.employee;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendance;
import com.quotorcloud.quotor.academy.api.entity.employee.EmployeeAttendanceRule;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeAttendanceVO;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeAttendanceMapper;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeAttendanceRuleMapper;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeMapper;
import com.quotorcloud.quotor.academy.service.employee.EmployeeAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工考勤信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-03
 */
@Service
@Slf4j
public class EmployeeAttendanceServiceImpl extends ServiceImpl<EmployeeAttendanceMapper, EmployeeAttendance> implements EmployeeAttendanceService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeAttendanceMapper employeeAttendanceMapper;

    @Autowired
    private EmployeeAttendanceRuleMapper employeeAttendanceRuleMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    /**
     * 打卡
     * @param employeeAttendance
     * @return
     */
    @Override
    public Boolean checkWorkAttendance(String userId) {
        //我得知道他是谁，然后是日期就可以完成打卡
        if(ComUtil.isEmpty(userId)){
            throw new MyException("未收到打卡人信息，请重新操作！");
        }
//        if(ComUtil.isEmpty(employeeAttendance.getDate())){
//            throw new MyException("未收到打卡日期，请重新操作！");
//        }
        EmployeeAttendance attendance = employeeAttendanceMapper.selectOne(new QueryWrapper<EmployeeAttendance>()
                .lambda().eq(EmployeeAttendance::getUserId, userId)
                .eq(EmployeeAttendance::getDate, LocalDate.now()));

        if(ComUtil.isEmpty(attendance)){
            throw new MyException("未找到属于您的考勤规则，请联系管理员进行处理");
        }
        //如果签到和签退都有值则不需要再打卡
        if(!ComUtil.isEmpty(attendance.getSignInTime()) && !ComUtil.isEmpty(attendance.getSignOutTime())){
            throw new MyException("今日打卡已完成，请勿多次操作哦！");
        }
        //如果签到时间不为空则是签退
        if(!ComUtil.isEmpty(attendance.getSignInTime())){
            //判断考勤状态
            LocalDateTime now = judgeAttendanceState(attendance);
            attendance.setSignOutTime(now);
        //如果签到时间为空则是签到
        }else {
            attendance.setSignInTime(LocalDateTime.now());
        }
        employeeAttendanceMapper.updateById(attendance);
        return Boolean.TRUE;
    }

    /**
     * 查询列表
     * @param page
     * @param employeeAttendance
     * @param shopId 店铺标识
     * @param date 日期
     * @return
     */
    @Override
    public IPage<EmployeeAttendanceVO> listCheckWorkAttendance(Page page, EmployeeAttendance employeeAttendance) {
        if(!ComUtil.isEmpty(employeeAttendance.getDateRange())){
            List<String> stringDate = DateTimeUtil.getStringDate(employeeAttendance.getDateRange());
            employeeAttendance.setStart(stringDate.get(0));
            employeeAttendance.setEnd(stringDate.get(1));
        }
        return employeeAttendanceMapper.listCheckWorkAttendance(page, employeeAttendance);
    }

    /**
     * 将打卡状态设为正常
     * @param id
     * @return
     */
    @Override
    public Boolean updateWorkAttendanceStateNormal(String id) {
        EmployeeAttendance employeeAttendance = new EmployeeAttendance();
        employeeAttendance.setId(id);
        //打卡状态设为正常
        employeeAttendance.setAttendanceState(CommonConstants.ATT_NORMAL);
        employeeAttendanceMapper.updateById(employeeAttendance);
        return Boolean.TRUE;
    }

    /**
     * 统计分析
     * @param page
     * @param employeeAttendance
     * @return
     */
    @Override
    public IPage statisticsAttendance(Page page, EmployeeAttendance employeeAttendance) {
        shopSetterUtil.shopSetter(employeeAttendance, employeeAttendance.getShopId());
        return employeeAttendanceMapper.selectAttendanceStatistics(page, employeeAttendance);
    }

    /**
     * 按月份查找
     * @param userId
     * @return
     */
    @Override
    public List<EmployeeAttendanceVO> listCheckWorkAttendanceByMonth(EmployeeAttendance employeeAttendance) {
        shopSetterUtil.shopSetter(employeeAttendance,employeeAttendance.getShopId());
        //首先查出数据库中存的数据
        List<EmployeeAttendanceVO> employeeAttendanceVOList = employeeAttendanceMapper.listCheckWorkAttendance(employeeAttendance);
        //根据日期分组
        Map<LocalDate, EmployeeAttendanceVO> dateMap = Maps.uniqueIndex(employeeAttendanceVOList, EmployeeAttendanceVO::getDate);;
        //获取传入月份中的所有天数
        List<String> monthEveryDayList = DateTimeUtil.getMonthEveryDayList(employeeAttendance.getDateMonth());
        //遍历天数
        for (String day:monthEveryDayList){
            LocalDate date = DateTimeUtil.stringToLocalDate(day);
            EmployeeAttendanceVO attendance = dateMap.get(date);
            //如果数据库数据当前日期里没有获取到日期，则新建一个只包含日期的对象，并add进结果集里
            if(ComUtil.isEmpty(dateMap.get(date))){
                EmployeeAttendanceVO employeeAttendanceVO = new EmployeeAttendanceVO();
                employeeAttendanceVO.setDate(date);
                employeeAttendanceVO.setWeek(DateTimeUtil.weekByDate(day));
                //不可修改
                employeeAttendanceVO.setUpdateAttState(2);
                employeeAttendanceVOList.add(employeeAttendanceVO);
            }else {
                //如果有把星期几设置一下
                attendance.setWeek(DateTimeUtil.weekByDate(day));
            }
        }
        return employeeAttendanceVOList;
    }

    /**
     * 考勤定时任务
     */
    @Override
    public void timerTaskAttendance() {
        //首先处理昨天没有考勤状态的数据，注意需要分工作日和非工作日
        List<EmployeeAttendance> employeeAttendances = employeeAttendanceMapper.selectList(Wrappers.<EmployeeAttendance>query().lambda().eq(EmployeeAttendance::getDate, LocalDate.now().minusDays(1)));
        //查询员工考勤列表
        List<EmployeeAttendanceRule> employeeAttendanceRules = employeeAttendanceRuleMapper.selectList(Wrappers.query());
        //查出店铺标识
        List<String> shopIdList = employeeAttendanceRules.stream().map(EmployeeAttendanceRule::getShopId).collect(Collectors.toList());
        //规则和标识对应上方便之后的获取
        Map<String, EmployeeAttendanceRule> ruleMap = Maps.uniqueIndex(employeeAttendanceRules, EmployeeAttendanceRule::getId);
        //找出没有考勤状态的数据
        if(!ComUtil.isEmpty(employeeAttendances)){
            List<EmployeeAttendance> attendNotStates = employeeAttendances.stream().filter(employeeAttendance -> ComUtil.isEmpty(employeeAttendance.getAttendanceState())).collect(Collectors.toList());
            //找出没有考勤状态的数据，加入状态
            if(!ComUtil.isEmpty(attendNotStates)){
                for (EmployeeAttendance employeeAttendance : attendNotStates){
                    EmployeeAttendanceRule rule = ruleMap.get(employeeAttendance.getAttendanceRuleId());
                    if(ComUtil.isEmpty(rule)){
                        continue;
                    }
                    //工作日集合
                    List<String> workdayList = Splitter.on(CommonConstants.SEPARATOR).splitToList(rule.getWorkday());
                    //当前工作日
                    Integer todayWeek = DateTimeUtil.weekIntegerByDate(DateTimeUtil.localDateToString(employeeAttendance.getDate()));
                    //工作日
                    if(workdayList.contains(todayWeek)){
                        //工作日为缺卡
                        employeeAttendance.setAttendanceState(CommonConstants.ATT_LACK_ATTENDANCE);
                        //可修改
                        employeeAttendance.setUpdateAttState(1);
                    }else {
                        //非工作日为正常
                        employeeAttendance.setAttendanceState(CommonConstants.ATT_NORMAL);
                        //不可修改
                        employeeAttendance.setUpdateAttState(2);
                    }
                }
            }
        }
        //查询出在职员工
        List<Employee> employees = employeeMapper.selectList(Wrappers.<Employee>query().lambda().eq(Employee::getDelState, 0).eq(Employee::getWorkState, 1).in(Employee::getShopId, shopIdList));
        //员工根据店铺信息分组
        Map<String, List<Employee>> empMap = employees.stream().collect(Collectors.groupingBy(Employee::getShopId));
        //由于插入数据操作较多，创建链表
        List<EmployeeAttendance> attendances = new LinkedList<>();
        //判断是否有离职员工和新增员工
        for (EmployeeAttendanceRule rule:employeeAttendanceRules){
            List<Employee> employeeListByShop = empMap.get(rule.getShopId());
            //规则里现在保存的集合
            List<String> inUserList = Splitter.on(CommonConstants.SEPARATOR).splitToList(rule.getEmpUserIdList());
            //最新查出来的集合
            List<String> nowUserList = employeeListByShop.stream().map(Employee::getUserId).collect(Collectors.toList());
            List<String> newUserList = new ArrayList<>();
            //全店适用的情况下
            if(rule.getApplyEmp() == 1){
                //首先看规则中有没有离职的员工
                for (String userId:inUserList){
                    if(nowUserList.contains(userId)){
                        newUserList.add(userId);
                    }
                }
                //再看有没有新加入的员工
                for (String userId:nowUserList){
                    if(!newUserList.contains(userId)){
                        newUserList.add(userId);
                    }
                }
            }else {
                //看规则中有没有离职的员工
                for (String userId:inUserList){
                    if(nowUserList.contains(userId)){
                        newUserList.add(userId);
                    }
                }
            }
            //新建当天的数据
            for (String userId:newUserList){
                EmployeeAttendance employeeAttendance = new EmployeeAttendance();
                employeeAttendance.setAttendanceRuleId(rule.getId());
                employeeAttendance.setUserId(userId);
                employeeAttendance.setDate(LocalDate.now());
                attendances.add(employeeAttendance);
            }
        }
        //批量插入
        this.saveBatch(attendances);
    }




    //判断考勤状态
    private LocalDateTime judgeAttendanceState(EmployeeAttendance attendance) {
        //获取签到时间
        LocalDateTime signInTime = attendance.getSignInTime();
        EmployeeAttendanceRule rule = employeeAttendanceRuleMapper.selectById(attendance.getAttendanceRuleId());
        if(ComUtil.isEmpty(rule)){
            throw new MyException("未找到相应的考勤规则，请联系管理员进行操作！");
        }
        //现在时长
        LocalDateTime now = LocalDateTime.now();
        //规则中规定的签退时间
        LocalTime endTime = rule.getEndTime();
        //规则中规定的签到时间
        LocalTime startTime = rule.getStartTime();
        //现在时间
        long nowSeconds = now.getHour()*3600+now.getMinute()*60+now.getSecond();
        //规定的签退时间
        long ruleSignOutSeconds = endTime.getHour()*3600+endTime.getMinute()*60+endTime.getSecond();
        //规定的签到时间
        long ruleSignInSeconds = startTime.getHour()*3600+endTime.getMinute()*60+startTime.getSecond();
        long signInSeconds = signInTime.getHour()*3600+signInTime.getMinute()*60+signInTime.getSecond();
        List<String> workdayList = Splitter.on(CommonConstants.SEPARATOR).splitToList(rule.getWorkday());
        Integer todayWeek = DateTimeUtil.weekIntegerByDate(DateTimeUtil.localDateToString(attendance.getDate()));
        //首先判断是否是工作日，工作日首先判断迟到跟早退的情况
        if(workdayList.contains(todayWeek)){
            //状态可修改
            attendance.setUpdateAttState(1);
            if(nowSeconds<ruleSignOutSeconds && signInSeconds>ruleSignInSeconds){
                //早退+迟到
                attendance.setAttendanceState(CommonConstants.ATT_BE_LATE_LEAVE_EARLY);
                attendance.setOvertime(CommonConstants.ATT_LEAVE_EARLY + ":"+ (nowSeconds-ruleSignOutSeconds) +","+ CommonConstants.ATT_BE_LATE +":"+ (signInSeconds-ruleSignInSeconds));

            }else if(signInSeconds>ruleSignInSeconds){
                //迟到
                attendance.setAttendanceState(CommonConstants.ATT_BE_LATE);
                attendance.setOvertime(CommonConstants.ATT_BE_LATE + (signInSeconds-ruleSignInSeconds));
            } else if(nowSeconds<ruleSignOutSeconds){
                //早退
                attendance.setAttendanceState(CommonConstants.ATT_LEAVE_EARLY);
                attendance.setOvertime(CommonConstants.ATT_LEAVE_EARLY + (ruleSignOutSeconds-nowSeconds));
            }else {
                //正常
                attendance.setAttendanceState(CommonConstants.ATT_NORMAL);
            }
        }
        //首先判断是否开启加班设置
        if(rule.getOvertimeStartUse() == 2){
            //如果未开启加班设置，非工作日打卡直接设置为正常，不去判断打卡时间
            if(!workdayList.contains(todayWeek)){
                //正常
                attendance.setAttendanceState(CommonConstants.ATT_NORMAL);
                attendance.setUpdateAttState(2);
            }
        }
        if(rule.getOvertimeStartUse() == 1){
            //开启加班，判断工作日是否有加班情况
            if(workdayList.contains(todayWeek)){
                if(nowSeconds>ruleSignOutSeconds+(rule.getStartOverworkTime()*60)){
                    //如果已经是迟到状态
                    if(attendance.getAttendanceState().equals(CommonConstants.ATT_BE_LATE)){
                        attendance.setOvertime(attendance.getAttendanceState() + ","+ CommonConstants.ATT_WORK_OVERTIME +":"+(nowSeconds-ruleSignOutSeconds+(rule.getStartOverworkTime()*60)));
                        attendance.setAttendanceState(CommonConstants.ATT_BE_LATE_WORK_OVERTIME);
                        attendance.setUpdateAttState(1);
                    }else {
                        //加班
                        attendance.setAttendanceState(CommonConstants.ATT_WORK_OVERTIME);
                        attendance.setOvertime(CommonConstants.ATT_WORK_OVERTIME + ":"+(nowSeconds-ruleSignOutSeconds+(rule.getStartOverworkTime()*60)));
                        attendance.setUpdateAttState(1);
                    }
                }
            }
            //判断不是工作日的情况
            if(!workdayList.contains(todayWeek)){
                if(rule.getNotWorkday() == 1){
                    //加班
                    attendance.setAttendanceState(CommonConstants.ATT_WORK_OVERTIME);
                    attendance.setOvertime(CommonConstants.ATT_WORK_OVERTIME + (nowSeconds-signInSeconds));
                    attendance.setUpdateAttState(1);
                }else {
                    //正常
                    attendance.setAttendanceState(CommonConstants.ATT_NORMAL);
                    attendance.setUpdateAttState(2);
                }
            }

        }
        return now;
    }


}
