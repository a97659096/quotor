package com.quotorcloud.quotor.academy.service.impl.member;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.quotorcloud.quotor.academy.api.dto.member.MemberDTO;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.employee.Employee;
import com.quotorcloud.quotor.academy.api.entity.member.Member;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCard;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCardDetail;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetail;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.quotorcloud.quotor.academy.api.vo.member.MemberCardVO;
import com.quotorcloud.quotor.academy.api.vo.member.MemberCountByShopIdVO;
import com.quotorcloud.quotor.academy.api.vo.member.MemberVO;
import com.quotorcloud.quotor.academy.api.vo.order.OrderWebVO;
import com.quotorcloud.quotor.academy.mapper.member.MemberCardMapper;
import com.quotorcloud.quotor.academy.mapper.member.MemberMapper;
import com.quotorcloud.quotor.academy.mapper.order.OrderMapper;
import com.quotorcloud.quotor.academy.service.employee.EmployeeService;
import com.quotorcloud.quotor.academy.service.member.MemberService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Override
    public Boolean saveMember(MemberDTO memberDTO) {
        Member member = new Member();
        //映射赋值
        mapMemberDODTO(memberDTO, member);
        member.setDelState(CommonConstants.STATUS_NORMAL);
        member.setMember(CommonConstants.NOT_MEMBER);
        //存入图片
        FileUtil.saveFileAndField(member, memberDTO, "headImg",
                FileConstants.FileType.FILE_MEMBER_IMG_DIR, null);
        memberMapper.insert(member);
        return Boolean.TRUE;
    }

    /**
     * 分页查询会员列表
     * @param page
     * @param memberDTO
     * @return
     */
    @Override
    public Page<MemberVO> listMember(Page page, MemberDTO memberDTO) {
        //对到店频次和时间分类进行处理
        checkFrequency(memberDTO);

        shopSetterUtil.shopSetter(memberDTO, memberDTO.getShopId());
        //分页查询会员信息和订单信息联查
        Page<MemberVO> memberVOPage = memberMapper
                .selectMemberOrderPage(page, memberDTO);
        List<MemberVO> records = memberVOPage.getRecords();
        if(!ComUtil.isEmpty(records)){
            List<String> memberIds = records.stream().map(MemberVO::getId)
                    .collect(Collectors.toList());
            MemberCard memberCard = new MemberCard();
            memberCard.setMemberIds(memberIds);
            /**
             * 查询会员卡详情
             */
            List<MemberCardVO> memberCardVOS = memberCardMapper.listMemberCard(memberCard);
            Map<String, List<MemberCardVO>> idCardMap = memberCardVOS.stream()
                    .collect(Collectors.groupingBy(MemberCardVO::getMemberId));

            /**
             * 查询消费详情
             */
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setMemberIds(memberIds);
            List<OrderWebVO> orderWebVOS = orderMapper.selectOrderWebVO(orderDTO);
            Map<String, List<OrderWebVO>> idOrderMap = orderWebVOS.stream()
                    .collect(Collectors.groupingBy(OrderWebVO::getMemberId));

            for (MemberVO memberVO : records) {
                mapMemberDOVO(memberVO);
                //拿到当前会员下所有订单信息
                List<OrderWebVO> orderWebs = idOrderMap.get(memberVO.getId());
                //计算累计消费、最近一次消费金额、最近一次消费次数、最后一次消费时间、当月到店次数、到店总次数
                if(!ComUtil.isEmpty(orderWebs)){
                    countMemberOrderInfo(memberVO, orderWebs);
                }
                //卡余计算
                List<MemberCardVO> memberCards = idCardMap.get(memberVO.getId());
                //计算卡余余额和卡余次数
                if(!ComUtil.isEmpty(memberCards)){
                    countMemberCardInfo(memberVO, memberCards);
                }
            }
        }
        return memberVOPage;
    }

    /**
     * 处理到店频次和时间分类
     * @param memberDTO
     */
    private void checkFrequency(MemberDTO memberDTO) {
        if(!ComUtil.isEmpty(memberDTO.getReachShopFrequency())){
            List<String> shopFrequencyMemberIds = orderMapper
                    .selectMemberReachShopFrequency(memberDTO.getReachShopFrequency());
            if(ComUtil.isEmpty(shopFrequencyMemberIds)){
                memberDTO.setShopFrequencyMemberIds(Lists.newArrayList("null"));
            }else {
                memberDTO.setShopFrequencyMemberIds(shopFrequencyMemberIds);
            }
        }
        if(!ComUtil.isEmpty(memberDTO.getNotReachShopDays())){
            List<String> notReachShopMemberIds = orderMapper.selectNotReachShopDays(memberDTO.getNotReachShopDays());
            if(ComUtil.isEmpty(notReachShopMemberIds)){
                memberDTO.setShopNotReachShopMemberIds(Lists.newArrayList("null"));
            }else {
                memberDTO.setShopNotReachShopMemberIds(notReachShopMemberIds);
            }
        }
    }

    /**
     * 计算卡余额和卡次数
     * @param memberVO
     * @param memberCards
     */
    private void countMemberCardInfo(MemberVO memberVO, List<MemberCardVO> memberCards) {
        BigDecimal totalCardMarginMoney = new BigDecimal(0);
        Integer totalCardMarginTimes = 0;
        for (MemberCardVO memberCardVO:memberCards){
            //为储值卡时才有卡余
            if(memberCardVO.getCardTypeWay().equals(1)){
                totalCardMarginMoney = totalCardMarginMoney.add(memberCardVO.getCardSurplus());
                //总余次就是把总卡次的次数加上
            }else {
                totalCardMarginTimes = totalCardMarginTimes +
                        Integer.valueOf(memberCardVO.getCardSurplus().toString());
            }
            //卡内容里的次数相加
            List<MemberCardDetail> memberCardDetailList = memberCardVO.getMemberCardDetailList();
            for (MemberCardDetail memberCardDetail:memberCardDetailList){
                //当卡内容详情为次数时，进行追加
                if(memberCardDetail.getContentType().equals(3) && !ComUtil.isEmpty(memberCardDetail.getContentSurplus())){
                    totalCardMarginTimes = totalCardMarginTimes + Integer.valueOf(memberCardDetail
                            .getContentSurplus().toString());
                }
            }
        }
        memberVO.setCardMarginMoney(totalCardMarginMoney);
        memberVO.setCardMarginTimes(totalCardMarginTimes);
    }

    /**
     * 计算累计消费、最近一次消费金额、最近一次消费次数、最后一次消费时间、当月到店次数、到店总次数
     * @param memberVO
     * @param orderWebs
     */
    private void countMemberOrderInfo(MemberVO memberVO, List<OrderWebVO> orderWebs) {
        //排序保证不为空的情况下最近一条订单记录在第一个
        Collections.sort(orderWebs);
        //累计消费
        BigDecimal addUpConsume = new BigDecimal(0);
        //最近一次消费金额
        BigDecimal lastConsumeMoney = new BigDecimal(0);
        //最近一次消费次数
        BigDecimal lastConsumeTimes = new BigDecimal(0);
        //最后一次消费时间
        LocalDateTime lastConsumeTime = null;
        //当月到店次数
        Integer reachShopTimesByMonth = 0;
        //到店总次数
        Integer reachShopTimes =0;
        boolean isLast = true;
        //循环订单表
        for (OrderWebVO orderWebVO :orderWebs){
            reachShopTimes++;
            List<OrderDetail> orderDetails = orderWebVO.getOrderDetails();
            long timestamp = DateTimeUtil.localdatetimeToTimestamp(orderWebVO.getGmtCreate());
            //判断是否为当月
            if(DateTimeUtil.isThisMonth(timestamp)){
                reachShopTimesByMonth++;
            }
            //循环订单详情表
            for (OrderDetail orderDetail : orderDetails){
                List<OrderDetailPay> orderDetailPays = orderDetail.getOrderDetailPays();
                //循环订单支付方式表
                for (OrderDetailPay orderDetailPay : orderDetailPays){
                    //支出方式为业绩时才计算入累计消费
                    if(orderDetailPay.getPayType().equals(2)){
                        addUpConsume = addUpConsume.add(orderDetailPay.getPayMoney());
                    }
                    //只循环第一次则置为false
                    if(isLast) {
                        //最后一次消费金额
                        if (orderDetailPay.getPayMoneyType().equals(1)) {
                            lastConsumeMoney = lastConsumeMoney.add(orderDetailPay.getPayMoney());
                        }
                        //最后一次消费次数
                        if (orderDetailPay.getPayMoneyType().equals(2)) {
                            lastConsumeTimes = lastConsumeTimes.add(orderDetailPay.getPayMoney());
                        }
                        lastConsumeTime = orderDetail.getGmtCreate();
                    }
                }
            }
            isLast = false;
        }
        memberVO.setAddUpConsumeMoney(addUpConsume);
        memberVO.setArriveShopTimes(reachShopTimes);
        memberVO.setArriveShopTimesByMonth(reachShopTimesByMonth);
        memberVO.setLastConsumeMoney(lastConsumeMoney);
        memberVO.setLastConsumeTimes(Integer.valueOf(lastConsumeTimes.toString()));
        memberVO.setLastConsumeTime(lastConsumeTime);
    }

    @Override
    public Boolean updateMember(MemberDTO memberDTO) {
        Member member = this.getById(memberDTO.getId());
        //刪除圖片
        FileUtil.removeFileAndField(member, memberDTO, "headImg", FileConstants.FileType.FILE_MEMBER_IMG_DIR);
        //保存圖片
        FileUtil.saveFileAndField(member, memberDTO, "headImg", FileConstants.FileType.FILE_MEMBER_IMG_DIR, null);

        BeanUtils.copyProperties(memberDTO, member, "headImg", "traceEmployee");
        mapMemberDODTO(memberDTO, member);

        memberMapper.updateById(member);

        return Boolean.TRUE;
    }

    @Override
    public Boolean removeMember(String id) {
        memberMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public MemberVO getMemberById(String id) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        MemberVO memberVO = memberMapper.selectMemberById(id);
        if(!ComUtil.isEmpty(memberVO)){
            mapMemberDOVO(memberVO);
        }
        return memberVO;
    }

    @Override
    public List<JSONObject> selectMemberListBox(String shopId) {
        //获取店铺信息
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return null;
        }
        if(ComUtil.isEmpty(user.getDeptId())){
            return null;
        }
        List<Member> members = memberMapper.selectList(new QueryWrapper<Member>().eq("del_state", CommonConstants.STATUS_NORMAL)
                .eq("shop_id", ComUtil.isEmpty(shopId) ? user.getDeptId() : shopId));
        return members.stream().map(member -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", member.getId());
            jsonObject.put("name", member.getName());
            return jsonObject;
        }).collect(Collectors.toList());
    }

    /**
     * 计算会员总个数
     * @param memberDTO
     * @return
     */
    @Override
    public JSONObject countMemberTimes(MemberDTO memberDTO) {
        List<MemberVO> memberVOS = memberMapper.selectMemberOrderPage(memberDTO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberTimes", memberVOS.size());
        return jsonObject;
    }

    /**
     * 计算会员总个数并根据店铺标识分组展示，并且计算出会员增长率
     * @param memberDTO
     * @return
     */
    @Override
    public JSONObject countMemberTimesGroupByShopId(MemberDTO memberDTO) {
        List<MemberVO> memberVOS = memberMapper.selectMemberOrderPage(memberDTO);
//        int yesterdayMemberCounts = memberVOS.size();
//        int todayMemberCounts = memberVOS.size();
//        for (MemberVO memberVO:memberVOS){
//            if(DateTimeUtil.localDateToString(memberVO.getJoinDate())
//                    .equals(DateTimeUtil.localDateToString(LocalDate.now()))){
//                yesterdayMemberCounts--;
//            }
//        }
//        int diffValue = todayMemberCounts - yesterdayMemberCounts;
//        //增长率
//        BigDecimal rateOfIncrease = BigDecimal.valueOf(diffValue)
//                .divide(BigDecimal.valueOf(yesterdayMemberCounts));

        //分组查询
        Map<String, List<MemberVO>> membersByShopId = memberVOS.stream()
                .collect(Collectors.groupingBy(MemberVO::getShopId));

        List<MemberCountByShopIdVO> memberCountByShopIdVOS = new ArrayList<>();
        Integer memberSize = 0;
        for (String shopId:membersByShopId.keySet()){
            //会员列表
            List<MemberVO> memberList = membersByShopId.get(shopId);
            if(!ComUtil.isEmpty(memberList)){
                memberCountByShopIdVOS.add(MemberCountByShopIdVO.builder().peopleCounts(memberList.size())
                        .shopId(shopId).shopName(memberList.get(0).getShopName())
                        .shopHeadImg(memberList.get(0).getShopHeadImg()).build());
                memberSize = memberSize+memberList.size();
            }

        }
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("rateOfIncrease", rateOfIncrease);
        jsonObject.put("list", memberCountByShopIdVOS);
        jsonObject.put("totalMemberSize", memberSize);
        return jsonObject;
    }


    private void mapMemberDOVO(MemberVO memberVO) {
        //遍历结果集
        //关联员工获取出来
        String traceEmployeeDataBase = memberVO.getTraceEmployeeDataBase();
        if(!ComUtil.isEmpty(traceEmployeeDataBase)){
            //用，拆分开
            List<String> traceList = Splitter.on(CommonConstants.SEPARATOR)
                    .splitToList(traceEmployeeDataBase);
            // id:name  把name取出来
            List<String> traceEmployeeList = traceList.stream().map(trace ->
                    trace.substring(trace.indexOf(":") + 1))
                    .collect(Collectors.toList());
            //set进去
            memberVO.setTraceEmployee(traceEmployeeList);
        }

    }


    private void mapMemberDODTO(MemberDTO memberDTO, Member member) {
        BeanUtils.copyProperties(memberDTO, member, "headImg", "traceEmployee");

        List<String> traceEmployee = memberDTO.getTraceEmployee();

        //存入跟踪员工
        if(!ComUtil.isEmpty(traceEmployee)){
            //查询员工信息
            List<Employee> employees = (List<Employee>) employeeService.listByIds(traceEmployee);
            //将员工信息中的id和name用:分开add进一个数组  id:name
            List<String> idNameMap = employees.stream().map(employee -> employee.getId() + ":" + employee.getName())
                    .collect(Collectors.toList());
            //然后再用，把集合合并， id:name,id:name
            String traceEmp = Joiner.on(CommonConstants.SEPARATOR).join(idNameMap);
            member.setTraceEmployee(traceEmp);
        }

        //获取用户信息并赋值
        shopSetterUtil.shopSetter(member, memberDTO.getShopId());

        //推荐人
        if(!ComUtil.isEmpty(memberDTO.getReferrerId())){
            Member byId = this.getById(memberDTO.getReferrerId());
            member.setReferrerId(byId.getId());
            member.setReferreName(byId.getName());
        }
    }
}
