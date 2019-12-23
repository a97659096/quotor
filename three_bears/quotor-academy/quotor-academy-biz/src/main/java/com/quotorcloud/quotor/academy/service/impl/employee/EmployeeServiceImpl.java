package com.quotorcloud.quotor.academy.service.impl.employee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quotorcloud.quotor.academy.api.dto.employee.EmployeeDTO;
import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.quotorcloud.quotor.academy.api.entity.employee.OrderDetailServiceStaff;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetail;
import com.quotorcloud.quotor.academy.api.vo.Pager;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeePerformanceVO;
import com.quotorcloud.quotor.academy.api.vo.employee.EmployeeVO;
import com.quotorcloud.quotor.academy.api.vo.user.UserVO;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeMapper;
import com.quotorcloud.quotor.academy.mapper.employee.OrderDetailServiceStaffMapper;
import com.quotorcloud.quotor.academy.service.employee.EmployeeService;
import com.quotorcloud.quotor.academy.service.ListBoxService;
import com.quotorcloud.quotor.admin.api.entity.SysDept;
import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.constant.ListBoxConstants;
import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.*;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工信息表 服务实现类
 * </p>
 * TODO 预约排序功能待补充
 * @author tianshihao
 * @since 2019-10-29
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ListBoxService listBoxService;

    @Autowired
    private RemoteDeptService remoteDeptService;

    /**
     * 订单中服务员工mapper 由此查出业绩信息
     */
    @Autowired
    private OrderDetailServiceStaffMapper orderDetailServiceStaffMapper;

    private static String HEAD_IMG = "headImg/";

    private static String WORK_LIST = "worksList/";

    /**
     * 查询职位和头衔
     * @return
     */
    @Override
    public JSONObject selectEmployeePositionAndHeadTitle() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("position", listBoxService.listBox(ListBoxConstants.EMPLOYEE_MODULE, ListBoxConstants.EMPLOYEE_MODULE_POSITION));
        jsonObject.put("headTitle", listBoxService.listBox(ListBoxConstants.EMPLOYEE_MODULE, ListBoxConstants.EMPLOYEE_MODULE_HEAD));
        return jsonObject;
    }

    /**
     * 查询员工列表
     * @param shopId
     * @return
     */
    @Override
    public List<JSONObject> selectEmployeeListBox(String shopId) {
        //获取店铺信息
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return null;
        }
        if(ComUtil.isEmpty(user.getDeptId())){
            return null;
        }
        //查询店铺下的员工
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>()
                //未删除
                .eq("e_del_state", CommonConstants.STATUS_NORMAL)
                //在职
                .eq("e_work_state", 1)
                //店铺标识
                .eq("e_shop_id", ComUtil.isEmpty(shopId)?user.getDeptId():shopId));
        //转换格式进行拼接员工列表
        return employees.stream().map(employee -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", employee.getId());
            jsonObject.put("name", employee.getName());
            return jsonObject;
        }).collect(Collectors.toList());
    }

    /**
     * 查询员工业绩按门店分组
     * @param employeeDTO
     * @return
     */
    @Override
    public JSONObject selectEmployeePerformanceGroupByShopId(EmployeeDTO employeeDTO) {

        List<EmployeePerformanceVO> employeePerformanceVOS = employeeMapper.selectEmployeePerformance(employeeDTO);
        Map<String, List<EmployeePerformanceVO>> performanceByShopIdMap = employeePerformanceVOS.stream()
                .collect(Collectors.groupingBy(EmployeePerformanceVO::getShopId));
        JSONObject jsonObject = new JSONObject();
        getEcharts(employeeDTO, employeePerformanceVOS, jsonObject);
        R r = remoteDeptService.listDeptAll();
        List<SysDept> sysDepts = JSON.parseArray(JSON.toJSONString(r.getData()), SysDept.class);
        Map<Integer, SysDept> deptById = Maps.uniqueIndex(sysDepts, SysDept::getDeptId);

        List<JSONObject> jsonObjects = new ArrayList<>();
        for (String shopId:performanceByShopIdMap.keySet()){

            JSONObject json = new JSONObject();
            List<EmployeePerformanceVO> employeePerformances = performanceByShopIdMap.get(shopId);
            BigDecimal performanceByShopId = employeePerformances.stream()
                    .map(EmployeePerformanceVO::getPerformance).reduce(BigDecimal::add).get();

            json.put("shopId", shopId);
            json.put("shopName", deptById.get(Integer.valueOf(shopId)).getName());
            json.put("shopHeadImg", deptById.get(Integer.valueOf(shopId)).getHeadImg());
            json.put("performance", performanceByShopId);
            jsonObjects.add(json);

        }
        jsonObject.put("list", jsonObjects);
        return jsonObject;
    }

    /**
     * 获取echarts图
     * @param dateType
     * @param employeePerformanceVOS
     * @param jsonObject
     */
    private void getEcharts(EmployeeDTO employeeDTO, List<EmployeePerformanceVO> employeePerformanceVOS, JSONObject jsonObject) {

        Integer dateType = employeeDTO.getDateType();
        switch (dateType){
            case 1:
                employeeDTO.setStart(DateTimeUtil.getWeekStartDate());
                break;
            case 2:
                employeeDTO.setAppointDate(DateTimeUtil.localDateToString(LocalDate.now()).substring(0,7));
                break;
            case 3:
                employeeDTO.setAppointDate(DateTimeUtil.localDateToString(LocalDate.now()).substring(0,4));
                break;
        }

        List<String> x = new LinkedList<>();
        List<BigDecimal> y = new LinkedList<>();
        BigDecimal totalMoney = new BigDecimal(0);
        if(dateType.equals(1) || dateType.equals(2)){
            //分组根据日期
            Map<String, List<EmployeePerformanceVO>> map = new HashMap<>();
            for (EmployeePerformanceVO employeePerformanceVO:employeePerformanceVOS){
                String date = DateTimeUtil.localDatetimeToString(employeePerformanceVO.getGmtCreate());
                if(map.keySet().contains(date)){
                    map.get(date).add(employeePerformanceVO);
                }else {
                    List<EmployeePerformanceVO> expendList = Lists.newArrayList(employeePerformanceVO);
                    map.put(date, expendList);
                }
            }
            Set<String> dates = DateTimeUtil.getOrderByDate(map.keySet());
            for (String date: dates){
                BigDecimal money = map.get(date).stream().map(EmployeePerformanceVO::getPerformance)
                        .reduce(BigDecimal::add).get();
                x.add(date);
                y.add(money);
                totalMoney = totalMoney.add(money);
            }
        }else {
            //分组根据月份
            Map<String, List<EmployeePerformanceVO>> map = new HashMap<>();
            for (EmployeePerformanceVO employeePerformanceVO:employeePerformanceVOS){
                String date = DateTimeUtil.localDatetimeToMonth(employeePerformanceVO.getGmtCreate());
                if(map.keySet().contains(date)){
                    map.get(date).add(employeePerformanceVO);
                }else {
                    List<EmployeePerformanceVO> expendList = Lists.newArrayList(employeePerformanceVO);
                    map.put(date, expendList);
                }
            }
            Set<String> strings = new HashSet<>(map.keySet());
            for (String date:strings){
                BigDecimal money = map.get(date).stream().map(EmployeePerformanceVO::getPerformance)
                        .reduce(BigDecimal::add).get();
                x.add(date);
                y.add(money);
                totalMoney = totalMoney.add(money);
            }
        }
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        jsonObject.put("total", totalMoney);
    }

    /**
     * 查询员工业绩
     * @param employeeDTO
     * @return
     */
    @Override
    public JSONObject selectEmployeePerFormance(EmployeeDTO employeeDTO) {
        List<EmployeePerformanceVO> employeePerformanceVOs =
                employeeMapper.selectEmployeePerformance(employeeDTO);
        JSONObject jsonObject = new JSONObject();
        getEcharts(employeeDTO, employeePerformanceVOs, jsonObject);
        jsonObject.put("list", employeePerformanceVOs);
        return jsonObject;
    }

    /**
     * 查询员工业绩汇总查询
     * @param employeeDTO
     * @return
     */
    @Override
    public Pager selectEmployeePerformanceGroupById(Page page, EmployeeDTO employeeDTO) {
        List<EmployeeVO> employeeVOS = employeeMapper.selectEmployeePerformanceAll(employeeDTO);
        List<EmployeePerformanceVO> employeePerformanceVOS = new ArrayList<>();
        for (EmployeeVO employeeVO : employeeVOS){
            //获取服务员工列表
            List<OrderDetailServiceStaff> serviceEmployees = employeeVO.getServiceEmployees();
            //业绩
            BigDecimal performance = new BigDecimal(0);
            //卡耗金额
            BigDecimal cardConsumeMoney = new BigDecimal(0);
            //卡耗次数
            Integer cardConsumeTimes = 0;
            //开卡数
            Integer openCardTimes = 0;
            //项目数
            Integer projectTimes = 0;
            //产品数
            Integer productTimes = 0;
            //服务会员数
            Integer memberTimes = 0;
            //服务散客数
            Integer notMemberTiems = 0;
            //分配订单提成
            BigDecimal allotCommission = new BigDecimal(0);
            //阶梯订单提成
            BigDecimal ladderCommission = new BigDecimal(0);
            for (OrderDetailServiceStaff orderDetailServiceStaff : serviceEmployees){
                performance = performance.add(orderDetailServiceStaff.getPerformance());
                //金额
                if(orderDetailServiceStaff.getCardExpendUnit().equals(1)){
                    cardConsumeMoney = cardConsumeMoney.add(orderDetailServiceStaff.getCardExpend());
                //次数
                }else {
                    cardConsumeTimes = cardConsumeTimes + Integer.valueOf(orderDetailServiceStaff.getCardExpend().toString());
                }
                //分配提成
                if(orderDetailServiceStaff.getCommissionType().equals(1)){
                    allotCommission = allotCommission.add(orderDetailServiceStaff.getCommission());
                //阶梯提成
                }else {
                    ladderCommission = ladderCommission.add(orderDetailServiceStaff.getCommission());
                }
                OrderDetail orderDetail = orderDetailServiceStaff.getOrderDetail();
                switch (orderDetail.getContentType()){
                    case 1:
                        projectTimes++;
                        break;
                    case 2:
                        productTimes++;
                        break;
                    case 4:
                        openCardTimes++;
                        break;
                }

                switch (orderDetail.getMemberType()){
                    case 1:
                        memberTimes++;
                        break;
                    case 2:
                        notMemberTiems++;
                        break;
                }
            }
            EmployeePerformanceVO employeePerformanceVO = new EmployeePerformanceVO();
            employeePerformanceVO.setEmployeeId(employeeVO.getId());
            employeePerformanceVO.setEmployeeName(employeeVO.getName());
            employeePerformanceVO.setEmployeeHeadImg(employeeVO.getHeadImg());
            employeePerformanceVO.setEmployeeJobNumber(employeeVO.getJobNumber());
            employeePerformanceVO.setEmployeePosition(employeeVO.getPositionName());
            employeePerformanceVO.setEmployeeSex(employeeVO.getSex());
            employeePerformanceVO.setPerformance(performance);
            employeePerformanceVO.setCardConsumeMoney(cardConsumeMoney);
            employeePerformanceVO.setCardConsumeTimes(cardConsumeTimes);
            employeePerformanceVO.setOpenCardTimes(openCardTimes);
            employeePerformanceVO.setProductTimes(productTimes);
            employeePerformanceVO.setProjectTimes(projectTimes);
            employeePerformanceVO.setServiceMemberTimes(memberTimes);
            employeePerformanceVO.setServiceNotMemberTimes(notMemberTiems);
            //封装
            employeePerformanceVOS.add(employeePerformanceVO);
        }
        //根据业绩排序倒序
        if(!ComUtil.isEmpty(employeeDTO.getPerformanceSort()) && employeeDTO.getPerformanceSort().equals(1)){
            employeePerformanceVOS.stream().sorted((a,b) -> b.getPerformance().compareTo(a.getPerformance()));
        }
        //根据卡耗排序
        if(!ComUtil.isEmpty(employeeDTO.getCardConsumeSort()) && employeeDTO.getCardConsumeSort().equals(1)){
            employeePerformanceVOS.stream().sorted((a,b) -> b.getCardConsumeMoney().compareTo(a.getCardConsumeMoney()));
        }
        //根据提成排序
        if(!ComUtil.isEmpty(employeeDTO.getCommissionSort()) && employeeDTO.getCommissionSort().equals(1)){
            employeePerformanceVOS.stream().sorted((a,b) -> b.getAllotCommission().compareTo(a.getAllotCommission()));
        }

        Pager pager = new Pager();
        pager.setContent(employeePerformanceVOS);
        pager.setCurrentPage((int) page.getCurrent());
        pager.setPageSize((int) page.getSize());
        return pager;
    }


    @Override
    public IPage selectEmployeePerformanceDetails(Page page, EmployeeDTO employeeDTO) {
//        Page<EmployeeVO> employeeVOPage = employeeMapper.selectEmployeePerformanceAll(page, employeeDTO);
//        List<EmployeeVO> records = employeeVOPage.getRecords();
//        if(!ComUtil.isEmpty(records)){
//            for (EmployeeVO employeeVO:records){
//                List<OrderDetailServiceStaff> serviceEmployees = employeeVO.getServiceEmployees();
//                for (OrderDetailServiceStaff orderDetailServiceStaff:serviceEmployees){
//                    OrderDetail orderDetail = orderDetailServiceStaff.getOrderDetail();
//                    EmployeePerformanceVO.builder().employeeId(employeeVO.getId()).employeeName(employeeVO.getName())
//                            .employeeHeadImg(employeeVO.getHeadImg()).employeeJobNumber(employeeVO.getJobNumber())
//                            .employeePosition(employeeVO.getPositionName()).employeeSex(employeeVO.getSex())
//                            .performance(orderDetailServiceStaff.getPerformance())
//                            .cardConsumeTimes(orderDetailServiceStaff.getCardExpendUnit().equals(1)?0:Integer.valueOf(String.valueOf(orderDetailServiceStaff.getCardExpend())))
//                            .cardConsumeMoney(orderDetailServiceStaff.getCardExpendUnit().equals(1)?orderDetailServiceStaff.getCardExpend(): BigDecimal.valueOf(0))
//                            .allotCommission(orderDetailServiceStaff.getCommission())
//                            .contentName();
//                }
//            }
//        }
        return employeeMapper.selectEmployeePerformance(page, employeeDTO);
    }

    /**
     * 查询用户信息 与员工表联查
     * @param userId
     * @return
     */
    @Override
    public UserVO selectUserInfoByUserId(String userId) {
        return employeeMapper.selectUserInfoByUserId(userId);
    }

    /**
     * 按id查询员工信息
     * @param id
     * @return
     */
    @Override
    public EmployeeVO selectEmployeeById(String id) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>()
                .eq("e_del_state", 0).eq("e_id",id));
        EmployeeVO employeeVO = getEmployeeVO(employee);
        //查询员工业绩信息
        List<OrderDetailServiceStaff> employeePerformance = orderDetailServiceStaffMapper.selectList(Wrappers.<OrderDetailServiceStaff>query().lambda()
                .eq(OrderDetailServiceStaff::getEmployeeId, employeeVO.getId()));
        //业绩求和
        employeeVO.setPerformance(employeePerformance.stream()
                .map(OrderDetailServiceStaff::getPerformance)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return employeeVO;
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     * @return
     */
    @Override
    public Boolean updateEmployee(EmployeeDTO employeeDTO) {
        String id = employeeDTO.getId();
        if(ComUtil.isEmpty(id)){
            throw new MyException(ExceptionEnum.NOT_FIND_ID);
        }
        Employee employee = employeeMapper.selectById(id);
        List<String> fields = Lists.newArrayList("headImg", "works");
        FileUtil.removeFileAndField(employee, employeeDTO, fields, FileConstants.FileType.FILE_EMPLOYEE_IMG_DIR_);
        employeeMapper.updateById(mapEmployeeDTOToDO(employeeDTO, employee));
        return Boolean.TRUE;
    }

    /**
     * 删除员工信息
     * @param id
     * @return
     */
    @Override
    public Boolean removeEmployee(String id) {
        if(ComUtil.isEmpty(id)){
            throw new MyException(ExceptionEnum.NOT_FIND_ID);
        }
        try {
            Employee employee = new Employee();
            employee.setId(id);
            employee.setDelState(CommonConstants.STATUS_DEL);
            employeeMapper.updateById(employee);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ExceptionEnum.DELETE_EMPLOYEE_FAILED);
        }
        return Boolean.TRUE;
    }

    /**
     * 查询员工信息
     * @param employeeDTO
     * @return
     */
    @Override
    public JSONObject listEmployee(EmployeeDTO employeeDTO) {
        //设置店铺信息
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return null;
        }
        employeeDTO.setShopId(String.valueOf(user.getDeptId()));
        //设置日期
        setEmployeeDTODate(employeeDTO);
        //封装Page
        Page<Employee> page = PageUtil.getPage(employeeDTO.getPageNo(), employeeDTO.getPageSize());
        //查询员工信息
        IPage<Employee> employeeIPage = employeeMapper.selectPage(page, new QueryWrapper<Employee>()
                .eq("e_del_state", 0)
                .like(!ComUtil.isEmpty(employeeDTO.getName()), "e_name", employeeDTO.getName())
                .eq(!ComUtil.isEmpty(employeeDTO.getPositionName()), "e_position_name", employeeDTO.getPositionName())
                .eq("e_shop_id", employeeDTO.getShopId())
                .ge(!ComUtil.isEmpty(employeeDTO.getStart()), "e_join_date", employeeDTO.getStart())
                .le(!ComUtil.isEmpty(employeeDTO.getEnd()), "e_join_date", employeeDTO.getEnd())
                .like(!ComUtil.isEmpty(employeeDTO.getJobNumber()), "e_job_number", employeeDTO.getJobNumber())
                .like(!ComUtil.isEmpty(employeeDTO.getWorkState()), "e_work_state", employeeDTO.getWorkState())
                .orderByDesc("e_gmt_create"));
        List<Employee> records = employeeIPage.getRecords();
        List<EmployeeVO> employeeVOS = Lists.newLinkedList();
        //对特殊字段进行转换重新赋值
        for (Employee employee : records){
            EmployeeVO employeeVO = getEmployeeVO(employee);
            employeeVOS.add(employeeVO);
        }
        return PageUtil.getPagePackage("employees", employeeVOS, page);
    }

    private void setEmployeeDTODate(EmployeeDTO employeeDTO){
        List<String> date = DateTimeUtil.getStringDate(employeeDTO.getDateRange());
        if(!ComUtil.isEmpty(date)){
            employeeDTO.setStart(date.get(0));
            employeeDTO.setEnd(date.get(1));
        }
    }

    /**
     * DOVO映射
     * @param employee
     * @return
     */
    private EmployeeVO getEmployeeVO(Employee employee) {
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVO);
        if(!ComUtil.isEmpty(employee.getJoinDate())){
            employeeVO.setJoinDate(DateTimeUtil.localDateToString(employee.getJoinDate()));
        }
        //需要加前缀的字段
        FileUtil.addPrefix(employee, EmployeeVO.class, employeeVO, "works");
        return employeeVO;
    }

    /**
     * 新增员工信息
     * TODO 店铺信息未填充
     * @param employeeDTO
     * @return
     */
    @Override
    public Boolean saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        try {
            employeeMapper.insert(mapEmployeeDTOToDO(employeeDTO, employee));
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ExceptionEnum.SAVE_EMPLOYEE_FAILED);
        }
        return Boolean.TRUE;
    }

    private Employee mapEmployeeDTOToDO(EmployeeDTO employeeDTO, Employee employee){
        //映射
        BeanUtils.copyProperties(employeeDTO, employee, "eWorks", "eHeadImg", "joinDate");
        //设置店铺信息
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return null;
        }
        employee.setShopId(String.valueOf(user.getDeptId()));
        employee.setShopName(user.getDeptName());

        //处理职位
        listBoxService.checkListBox(employee.getPositionName(), ListBoxConstants.EMPLOYEE_MODULE,
                ListBoxConstants.EMPLOYEE_MODULE_POSITION);
        //处理头衔
        listBoxService.checkListBox(employee.getHeadTitleName(), ListBoxConstants.EMPLOYEE_MODULE,
                ListBoxConstants.EMPLOYEE_MODULE_HEAD);

        //处理日期
        if(!ComUtil.isEmpty(employeeDTO.getJoinDate())){
            employee.setJoinDate(DateTimeUtil.stringToLocalDate(employeeDTO.getJoinDate()));
        }

        //处理头像和作品集（通过反射得到方法信息进行操作）
        FileUtil.saveFileAndField(employee, employeeDTO,
                Lists.newArrayList("headImg","works"),
                FileConstants.FileType.FILE_EMPLOYEE_IMG_DIR_, null);
        return employee;
    }
}
