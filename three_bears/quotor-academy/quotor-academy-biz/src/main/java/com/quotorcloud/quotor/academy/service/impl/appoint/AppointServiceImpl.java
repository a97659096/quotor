package com.quotorcloud.quotor.academy.service.impl.appoint;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointContent;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointDTO;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointEmployeeDTO;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointProject;
import com.quotorcloud.quotor.academy.api.entity.appoint.Appoint;
import com.quotorcloud.quotor.academy.api.entity.appoint.AppointRoom;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionPro;
import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.quotorcloud.quotor.academy.api.entity.member.Member;
import com.quotorcloud.quotor.academy.api.vo.appoint.AppointVO;
import com.quotorcloud.quotor.academy.mapper.appoint.AppointMapper;
import com.quotorcloud.quotor.academy.mapper.appoint.AppointRoomMapper;
import com.quotorcloud.quotor.academy.mapper.employee.EmployeeMapper;
import com.quotorcloud.quotor.academy.mapper.member.MemberMapper;
import com.quotorcloud.quotor.academy.service.appoint.AppointService;
import com.quotorcloud.quotor.academy.service.condition.ConditionProService;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.GenerationSequenceUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 预约信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-21
 */
@Service
public class AppointServiceImpl extends ServiceImpl<AppointMapper, Appoint> implements AppointService {

    @Autowired
    private AppointMapper appointMapper;

    @Autowired
    private AppointRoomMapper appointRoomMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private OrderUtil orderUtil;

    @Autowired
    private ConditionProService conditionProService;

    @Override
    public Boolean saveAppoint(AppointDTO appointDTO) {
        List<Appoint> appoints = getAppoints(appointDTO);
        //批量插入
        this.saveBatch(appoints);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean updateAppoint(AppointDTO appointDTO) {
        //把预约信息删除
        this.remove(new QueryWrapper<Appoint>().eq("appoint_id", appointDTO.getAppointId()));
        //重新增加
        this.saveAppoint(appointDTO);
        return Boolean.TRUE;
    }

    @Override
    public Boolean cancelAppoint(String id) {
        Appoint appoint = new Appoint();
//        appoint.setAppointId(id);
        appoint.setAppointState(CommonConstants.APPOINT_LOSE_EFFICACY);
        appointMapper.update(appoint, new QueryWrapper<Appoint>().eq("appoint_id", id));
        return Boolean.TRUE;
    }

    @Override
    public List<AppointEmployeeDTO> formAppoint(AppointVO appointVO) {
        shopSetterUtil.shopSetter(appointVO, appointVO.getShopId());
        //分页查询出预约信息
        List<AppointVO> records = appointMapper.selectAppoint(appointVO);
        //获取员工信息
        List<AppointEmployeeDTO> employees = employeeMapper.selectAppointEmployee(appointVO.getShopId());

        AppointEmployeeDTO appointEmployeeDTO = new AppointEmployeeDTO();
        appointEmployeeDTO.setEmployeeName("未指定服务人员");
        employees.add(appointEmployeeDTO);

        Map<String, List<AppointDTO>> appointEmployeeMap = employees.stream()
                .collect(Collectors.toMap(AppointEmployeeDTO::getEmployeeId, AppointEmployeeDTO::getAppoints));
        //获取结果集
//        List<AppointVO> records = appointVOIPage.getRecords();
        //根据 appointId 分组
        Map<String, List<AppointVO>> listMap = records.stream().collect(Collectors.groupingBy(AppointVO::getAppointId));
        //遍历结果集，封装DTO返回给前端
        for (String appointId:listMap.keySet()){
            List<AppointVO> appointVOS = listMap.get(appointId);
            for (AppointVO appoint : appointVOS){
                //赋值appointDTO
                AppointDTO appointDTO = getAppointDTO(appointVOS, appoint);
                String appointStaffId = appoint.getAppointStaffId();
                //如果员工id为空则说明未指定服务人员
                if(ComUtil.isEmpty(appointStaffId)){
                    appointEmployeeDTO.getAppoints().add(appointDTO);
                }
                //如果含有这个员工则把此条预约信息放到员工信息下边的预约集合里
                if(!ComUtil.isEmpty(appointEmployeeMap.keySet().contains(appointStaffId))){
                    appointEmployeeMap.get(appointStaffId).add(appointDTO);
                }

            }
        }
        return employees;
    }

    @Override
    public IPage<AppointVO> listAppoint(Page<AppointVO> page, AppointVO appointVO) {
        return appointMapper.selectAppointPage(page, appointVO);
    }

    @Override
    public AppointDTO listAppointById(String id, String appointId) {
        AppointVO appointVO = new AppointVO();
        appointVO.setAppointId(appointId);
        List<AppointVO> records = appointMapper.selectAppoint(appointVO);
//        List<AppointVO> records = appointVOIPage.getRecords();
        Optional<AppointVO> optionalAppointVO = records.stream().filter(record -> record.getId().equals(id)).findFirst();
        return getAppointDTO(records, optionalAppointVO.get());
    }

    //对预约对象进行赋值
    private AppointDTO getAppointDTO(List<AppointVO> appointVOS, AppointVO appoint) {
        AppointDTO appointDTO = new AppointDTO();
        //属性拷贝
        BeanUtils.copyProperties(appoint, appointDTO);

        List<AppointContent> appointContents = new ArrayList<>();
        //再次遍历结果集，封装
        for (AppointVO appoint1 : appointVOS){
            AppointContent appointContent = setUpAppointContent(appoint1);
            //放入集合
            appointContents.add(appointContent);
        }
        //赋值
        appointDTO.setAppointContents(appointContents);
        return appointDTO;
    }

    //创建并封装预约内容对象
    private AppointContent setUpAppointContent(AppointVO appoint1) {
        //创建预约内容对象
        AppointContent appointContent = new AppointContent();
        //属性赋值
        BeanUtils.copyProperties(appoint1, appointContent);
        //获取项目信息
        String projects = appoint1.getProjects();
        //判断项目是否为空
        if(!ComUtil.isEmpty(projects)){
            List<AppointProject> appointProjects = setAppointProject(projects);
            //赋值
            appointContent.setAppointProjects(appointProjects);
        }
        return appointContent;
    }

    //创建并封装预约项目信息
    private List<AppointProject> setAppointProject(String projects) {
        //首先用逗号分开拆成 id:count
        List<String> projectList = Splitter.on(CommonConstants.SEPARATOR).splitToList(projects);
        List<String> projectIds = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        //建立map将id和count对应上，同时把所有id add进一个集合，方便批量查询，减少数据库操作
        for (String project:projectList){
            String[] split = project.split(":");
            map.put(split[0], Integer.valueOf(split[1]));
            projectIds.add(split[0]);
        }
        //批量查询
        List<ConditionPro> conditionPros = (List<ConditionPro>) conditionProService.listByIds(projectIds);
        //遍历结果集
        return conditionPros.stream().map(conditionPro -> {
            //封装项目对象
            AppointProject appointProject = new AppointProject();
            appointProject.setAppointProjectId(conditionPro.getId());
            appointProject.setAppointProjectName(conditionPro.getPName());
            //将次数也封装进去
            appointProject.setCounts(map.get(conditionPro.getId()));
            return appointProject;
        }).collect(Collectors.toList());
    }

    //插入逻辑
    private List<Appoint> getAppoints(AppointDTO appointDTO) {
        //客户标识
        String memberId;
        //是否是新客标识,初始化为不是新客
        Integer newMemberState = CommonConstants.NOT_NEW_MEMBER;
        //获取加盟商信息
        shopSetterUtil.shopSetter(appointDTO, appointDTO.getShopId());

        //首先判断是否是新客户，用手机号来判断
        Member member = memberMapper.selectOne(new QueryWrapper<Member>()
                .eq("phone", appointDTO.getMemberPhone())
                .eq("shop_id", appointDTO.getShopId()));
        //如果是新客户，则进行插入操作,并拿到新客户的id
        memberId = member.getId();
        if(ComUtil.isEmpty(member)){
            //拼装member
            Member newMember = new Member();
            newMember.setName(appointDTO.getMemberName());
            newMember.setPhone(appointDTO.getMemberPhone());
            memberMapper.insert(member);
            memberId = member.getId();
            //将状态设置为新客户
            newMemberState = CommonConstants.IS_NEW_MEMBER;
        }
        //遍历内容，封装Appoint对象，根据内容条数来决定有几个Appoint对象
        List<AppointContent> appointContents = appointDTO.getAppointContents();
        List<Appoint> appoints = new ArrayList<>();
        //生成预约ID号
        String appointId = GenerationSequenceUtil.generateUUID(null);
        //生成订单号
        String orderNumber = orderUtil.generateOrderSn();
        //遍历创建Appoint对象，进行批量插入
        for(AppointContent appointContent : appointContents){
            Appoint appoint = new Appoint();
            BeanUtils.copyProperties(appointDTO, appoint);
            //为预约id赋值
            appoint.setAppointId(appointId);
            appoint.setOrderNumber(orderNumber);
            //映射DTO与DO
            appoint.setMemberId(memberId);
            appoint.setNewCustomer(newMemberState);
            //映射主要内容,注意要查询房间和服务人员名称并赋值
            BeanUtils.copyProperties(appointContent, appoint);
            //房间名进行赋值
            if(!ComUtil.isEmpty(appoint.getRoomId())){
                AppointRoom appointRoom = appointRoomMapper.selectById(appoint.getRoomId());
                appoint.setRoomName(appointRoom.getName());
            }
            //皮肤管理师进行赋值
            if(!ComUtil.isEmpty(appoint.getAppointStaffId())){
                Employee employee = employeeMapper.selectById(appoint.getAppointStaffId());
                if(ComUtil.isEmpty(employee)){
                    appoint.setAppointStaffName("未指定皮肤管理师");
                }else {
                    appoint.setAppointStaffName(employee.getName());
                }
            }
            //处理项目信息，对标识和次数进行拼接并存入，id:2,id:3
            List<AppointProject> appointProjects = appointContent.getAppointProjects();

            //首先拼接成 id:2
            List<String> itemList = appointProjects.stream().map(appointProject ->
                    appointProject.getAppointProjectId() + ":" + appointProject.getCounts())
                    .collect(Collectors.toList());

            //再用，隔开拼成字符串
            appoint.setProjects(Joiner.on(CommonConstants.SEPARATOR).join(itemList));
            appoints.add(appoint);
        }
        return appoints;
    }

}
