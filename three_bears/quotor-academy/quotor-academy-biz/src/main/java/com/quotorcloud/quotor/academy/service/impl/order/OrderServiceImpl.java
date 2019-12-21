package com.quotorcloud.quotor.academy.service.impl.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardDetail;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionContentCommission;
import com.quotorcloud.quotor.academy.api.entity.employee.OrderDetailServiceStaff;
import com.quotorcloud.quotor.academy.api.entity.member.*;
import com.quotorcloud.quotor.academy.api.entity.order.Order;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetail;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.quotorcloud.quotor.academy.api.entity.order.OrderPayWay;
import com.quotorcloud.quotor.academy.api.vo.condition.ConditionCardVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.api.vo.member.MemberCardVO;
import com.quotorcloud.quotor.academy.api.vo.member.MemberVO;
import com.quotorcloud.quotor.academy.api.vo.order.OrderConsumeRecordVO;
import com.quotorcloud.quotor.academy.api.vo.order.OrderWebVO;
import com.quotorcloud.quotor.academy.mapper.appoint.AppointRoomMapper;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionCardMapper;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionContentCommissionMapper;
import com.quotorcloud.quotor.academy.mapper.member.*;
import com.quotorcloud.quotor.academy.mapper.order.OrderMapper;
import com.quotorcloud.quotor.academy.service.employee.OrderDetailServiceStaffService;
import com.quotorcloud.quotor.academy.service.member.MemberCardDetailService;
import com.quotorcloud.quotor.academy.service.order.OrderDetailPayService;
import com.quotorcloud.quotor.academy.service.order.OrderDetailService;
import com.quotorcloud.quotor.academy.service.order.OrderPayWayService;
import com.quotorcloud.quotor.academy.service.order.OrderService;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Autowired
    private AppointRoomMapper roomMapper;

    @Autowired
    private OrderUtil orderUtil;

    @Autowired
    private MemberCardDetailService memberCardDetailService;

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private ConditionContentCommissionMapper conditionContentCommissionMapper;

    @Autowired
    private OrderDetailPayService orderDetailPayService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailServiceStaffService orderDetailServiceStaffService;

    @Autowired
    private ConditionCardMapper conditionCardMapper;

    @Autowired
    private OrderPayWayService orderPayWayService;

    @Autowired
    private MemberIntegralMapper memberIntegralMapper;

    @Autowired
    private MemberIntegralRuleMapper memberIntegralRuleMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberLogMapper memberLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 按会员id查询消费记录
     * @param memberId
     * @return
     */
    @Override
    public IPage listConsumeRecordByMemberId(Page page, OrderDTO orderDTO) {
        IPage iPage = orderMapper.
                selectOrderWebVO(page, orderDTO);
        //获取结果集
        List<OrderWebVO> orderWebVOS = iPage.getRecords();
        //获取订单详情的所有标识
        if(ComUtil.isEmpty(orderWebVOS)){
            return null;
        }

        //将员工信息根据订单详情标识分组
        Map<String, List<OrderDetailServiceStaff>> orderDetailStaffMap
                = getStringListMap(orderWebVOS);

        List<OrderConsumeRecordVO> orderConsumeRecordVOS = new ArrayList<>();
        for (OrderWebVO orderWebVO:orderWebVOS){
            List<OrderDetail> orderDetails = orderWebVO.getOrderDetails();
            //服务员工，去重
            Set<String> staffs = new HashSet<>();
            //消费金额
            BigDecimal consumeMoney = new BigDecimal(0);
            //消费次数
            int consumeTimes = 0;
            //消费方式卡耗还是业绩，去重
            Set<Integer> consumeWay = new HashSet<>();
            for (OrderDetail orderDetail:orderDetails){
                //设置服务员工
                List<OrderDetailServiceStaff> orderDetailServiceStaffs = orderDetailStaffMap.get(orderDetail.getId());
                if(!ComUtil.isEmpty(orderDetailServiceStaffs)){
                    staffs.addAll(orderDetailServiceStaffs.stream()
                            .map(OrderDetailServiceStaff::getEmployeeName).collect(Collectors.toList()));
                }

                List<OrderDetailPay> orderDetailPays = orderDetail.getOrderDetailPays();
                for (OrderDetailPay orderDetailPay:orderDetailPays){
                    //为1时是金额
                    if(orderDetailPay.getPayMoneyType().equals(1)){
                        consumeMoney = consumeMoney.add(orderDetailPay.getPayMoney());
                    //为2时是次数
                    }else {
                        consumeTimes = consumeTimes + Integer.valueOf(orderDetailPay.getPayMoney().toString());
                    }
                    consumeWay.add(orderDetailPay.getPayType());
                }
            }
            //消费内容
            List<String> content = orderWebVO.getOrderDetails().stream().map(OrderDetail::getContentName).collect(Collectors.toList());
            orderConsumeRecordVOS.add(OrderConsumeRecordVO.builder()
                    .consumeContent(ComUtil.isEmpty(content)?null:Joiner.on(CommonConstants.SEPARATOR).join(content))
                    .consumeMoney(consumeMoney).consumeTimes(consumeTimes).consumeTime(orderWebVO.getKdTime())
                    .consumeWay(consumeWay).employeeNames(Joiner.on(CommonConstants.SEPARATOR).join(staffs))
                    .orderId(orderWebVO.getId()).orderNo(orderWebVO.getOrderNumber()).build());
        }
        iPage.setRecords(orderConsumeRecordVOS);
        return iPage;
    }


    /**
     * 把服务人员的信息根据订单详情变成id 分组，方便获取
     * @param orderWebVOS
     * @return
     */
    private Map<String, List<OrderDetailServiceStaff>> getStringListMap(List<OrderWebVO> orderWebVOS) {
        List<String> orderDetailIds = new ArrayList<>();
        //将所有订单详情id放到集合里
        orderWebVOS.forEach(orderWebVO -> {
            List<OrderDetail> orderDetails = orderWebVO.getOrderDetails();
            orderDetails.forEach(orderDetail -> orderDetailIds.add(orderDetail.getId()));
        });
        //查询出服务员工
        List<OrderDetailServiceStaff> staffList = orderDetailServiceStaffService.list(Wrappers.<OrderDetailServiceStaff>query()
                .lambda().in(OrderDetailServiceStaff::getOrderDetailId, orderDetailIds));
        //将服务员工根据订单详情分组
        return staffList.stream()
                .collect(Collectors.groupingBy(OrderDetailServiceStaff::getOrderDetailId));
    }

    /**
     * 开单，产品和项目
     * @param order
     * @return
     */
    @Override
    @Transactional
    public Boolean saveOrderPro(Order order) {
        //设置订单基础信息
        setOrderBasicsInfo(order);
        //订单类型设置为产品项目
        order.setType(1);
        //保存订单信息表
        this.save(order);
        BigDecimal money = new BigDecimal(0); //订单支付的总金额
        //订单中的内容名称
        List<String> content = new ArrayList<>();
        //获取订单详情
        if(!ComUtil.isEmpty(order.getOrderDetails())){
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails){
                //设置订单标识
                orderDetail.setOrderId(order.getId());
                orderDetailService.save(orderDetail);
                //设置支付和员工业绩
                setPaywayAndStaff(orderDetail, money);
                content.add(orderDetail.getContentName());
            }

            //设置积分
            setIntegral(order, money, content);

        }
        return Boolean.TRUE;
    }

    /**
     * 购卡开单
     * @param order
     * @return
     */
    @Override
    @Transactional
    public Boolean saveOrderCard(Order order) {

        setOrderBasicsInfo(order);
        //购卡
        order.setType(2);
        this.save(order);

        List<OrderDetail> orderDetails = order.getOrderDetails();

        if(!ComUtil.isEmpty(orderDetails)) {
            //订单支付的总金额
            BigDecimal money = new BigDecimal(0);
            //订单中的内容名称
            List<String> content = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetails) {
                //先保存订单详情
                orderDetail.setOrderId(order.getId());
                orderDetailService.save(orderDetail);
                //插入卡片
                MemberCard memberCard = new MemberCard();
                //为会员创建卡片
                ConditionCardVO conditionCard = conditionCardMapper.selectCardById(orderDetail.getContentId());
                //来源设置为购买
                memberCard.setCardSource(1);
                memberCard.setMemberId(order.getMemberId());
                memberCard.setShopId(order.getShopId());
                memberCard.setShopName(order.getShopName());
                //映射字段，插入卡片和内容
                mapMemberCard(memberCard, conditionCard);
                //设置支付方式和员工提成
                setPaywayAndStaff(orderDetail, money);
                //订单中的内容名称
                content.add(orderDetail.getContentName());
            }
            //设置积分
            setIntegral(order, money, content);
        }

        return Boolean.TRUE;
    }

    /**
     * 设置积分
     * @param order 订单实体类
     * @param money 消费金额
     * @param content 消费内容
     */
    private void setIntegral(Order order, BigDecimal money, List<String> content) {
        //查询积分规则，type为1 消费产生积分
        MemberVO memberVO = memberMapper.selectMemberById(order.getId());
        QuotorUser user = SecurityUtils.getUser();
        MemberIntegralRule memberIntegralRule = memberIntegralRuleMapper.selectOne(Wrappers.<MemberIntegralRule>query().lambda()
                .eq(MemberIntegralRule::getShopId, String.valueOf(order.getShopId()))
                .eq(MemberIntegralRule::getRuleType, 1));
        //计算积分
        if(!ComUtil.isEmpty(memberIntegralRule)){
            Integer applyMember = memberIntegralRule.getApplyMember();
            //获取消费金额
            BigDecimal consumerMoney = memberIntegralRule.getConsumerMoney();
            //取整倍数
            Integer multiple = Integer.valueOf(money.divide(consumerMoney)
                    .setScale(0, BigDecimal.ROUND_DOWN).toString());
            Integer giveIntegral = memberIntegralRule.getGiveIntegral();
            //算出积分
            Integer integral = multiple*giveIntegral;
            //插入操作
            MemberIntegral memberIntegral = new MemberIntegral();
            memberIntegral.setContent("购买:" + Joiner.on(CommonConstants.SEPARATOR).join(content));
            memberIntegral.setMemberId(memberVO.getId());
            memberIntegral.setMemberName(memberVO.getName());
            memberIntegral.setRuleId(memberIntegralRule.getId());
            memberIntegral.setRuleName(memberIntegralRule.getName());
            //现金消费
            memberIntegral.setRuleType(1);
            memberIntegral.setIntegral(integral);
            memberIntegral.setOperatorId(String.valueOf(user.getId()));
            memberIntegral.setOperatorName(user.getName());
            //全部客户
            if(applyMember.equals(1)){
                memberIntegralMapper.insert(memberIntegral);
            //持卡客户
            }else {
                if(memberVO.getMember().equals(1)){
                    //插入操作
                    memberIntegralMapper.insert(memberIntegral);
                }
            }
        }
    }

    private void mapMemberCard(MemberCard memberCard, ConditionCardVO conditionCard) {
//        memberCard.setMemberId(memberCard.getMemberId());
        memberCard.setCardTypeId(conditionCard.getTemplateTypeId());
        memberCard.setCardId(conditionCard.getId());
        memberCard.setCardTypeWay(conditionCard.getCardType());
        memberCard.setCardTypeName(conditionCard.getTemplateTypeName());
        memberCard.setCardName(conditionCard.getName());
        memberCard.setCardPrice(conditionCard.getPrice());
        //储值卡
        if(conditionCard.getCardType().equals(1)){
            memberCard.setCardTypeWay(1);
            //设置面额
            memberCard.setCardInitial(conditionCard.getDenomination());
            memberCard.setCardSurplus(conditionCard.getDenomination());
            //总次卡
        }else if(conditionCard.getCardType().equals(2)){
            memberCard.setCardTypeWay(2);
            memberCard.setCardInitial(conditionCard.getDenomination());
            memberCard.setCardSurplus(conditionCard.getDenomination());
        }else {
            memberCard.setCardTypeWay(3);
        }
        //生成卡号12位
        memberCard.setCardNumber(orderUtil.generateMemberNumber());
        //生成订单编号
        memberCard.setOrderNumber(orderUtil.generateOrderSn());

        //设置卡片的开始日期和结束日期
        setCardDateRange(memberCard, conditionCard);

        memberCard.setBuyDate(LocalDate.now());

        //存储卡信息
        memberCardMapper.insert(memberCard);
        //存储卡内容
        saveCardContent(memberCard, conditionCard);
        //存储卡赠送
        saveCardGive(memberCard, conditionCard);

    }

    /**
     * 保存卡赠品
     * @param memberCard
     * @param conditionCard
     */
    private void saveCardGive(MemberCard memberCard, ConditionCardVO conditionCard) {
        List<ConditionCardDetail> buyCardGive = conditionCard.getConditionCardDetails().stream()
                .filter(detail -> detail.getDetailType().equals(2)).collect(Collectors.toList());
        if(!ComUtil.isEmpty(buyCardGive)){
            List<MemberCardDetail> memberCardDetails = new ArrayList<>();
            for (ConditionCardDetail cardDetailsGive: buyCardGive){
                MemberCardDetail memberCardDetail = new MemberCardDetail();
                //类型设置为赠送
                memberCardDetail.setDetailType(2);
                memberCardDetail.setMemberCardId(memberCard.getId());
                memberCardDetail.setContentId(cardDetailsGive.getContentId());
                memberCardDetail.setContentName(cardDetailsGive.getContentName());
                memberCardDetail.setContent(cardDetailsGive.getContent());
                memberCardDetail.setContentType(cardDetailsGive.getContentType());
                //赠送了卡片
                if(memberCardDetail.getContentType().equals(4)){
                    String cardId = memberCardDetail.getContentId();
                    ConditionCardVO conditionCardVO = conditionCardMapper.selectCardById(cardId);
                    MemberCard giveCard = new MemberCard();
                    //来源设置为赠送
                    memberCard.setCardSource(2);
                    //再去插入卡片
                    mapMemberCard(giveCard, conditionCardVO);
                    //次数的话设置好始量和余量
                }else if(memberCardDetail.getContentType().equals(3)){
                    memberCardDetail.setContentInitial(memberCardDetail.getContent());
                    memberCardDetail.setContentSurplus(memberCardDetail.getContent());
                }
                memberCardDetails.add(memberCardDetail);
            }
            //插入
            memberCardDetailService.saveBatch(memberCardDetails);
        }
    }

    /**
     * 存储卡内容
     * @param memberCard
     * @param conditionCard
     */
    private void saveCardContent(MemberCard memberCard, ConditionCardVO conditionCard) {
        //插入卡内容
        List<ConditionCardDetail> conditionCardDetails = conditionCard.getConditionCardDetails().stream()
                .filter(detail -> detail.getDetailType().equals(1)).collect(Collectors.toList());
        List<MemberCardDetail> memberCardDetails = new ArrayList<>();
        for (ConditionCardDetail conditionCardDetail : conditionCardDetails){
            MemberCardDetail cardDetail = new MemberCardDetail();
            //卡内容
            cardDetail.setContentType(conditionCardDetail.getContentType());
            cardDetail.setDetailType(1);
            cardDetail.setMemberCardId(memberCard.getId());
            cardDetail.setContentId(conditionCardDetail.getContentId());
            cardDetail.setContentName(conditionCardDetail.getContentName());
            cardDetail.setContent(conditionCardDetail.getContent());
            cardDetail.setContentType(conditionCardDetail.getContentType());
            if(conditionCardDetail.getContentType().equals(3)){
                cardDetail.setContentInitial(conditionCardDetail.getContent());
                cardDetail.setContentSurplus(conditionCardDetail.getContent());
            }

            //为总次卡时把内容里的次数置为空
            if(conditionCard.getCardType().equals(2)){
                cardDetail.setContent(new BigDecimal(0));
                cardDetail.setContentInitial(new BigDecimal(0));
                cardDetail.setContentSurplus(new BigDecimal(0));
            }

            memberCardDetails.add(cardDetail);
        }
        memberCardDetailService.saveBatch(memberCardDetails);
    }

    private void setPaywayAndStaff(OrderDetail orderDetail, BigDecimal money) {
        //获取支付方式
        List<OrderDetailPay> orderDetailPays = orderDetail.getOrderDetailPays();
        //获取服务人员
        List<OrderDetailServiceStaff> serviceStaffs = orderDetail.getServiceStaffs();

        Integer unit = 1;
        BigDecimal cardConsumption = BigDecimal.valueOf(0);
        BigDecimal performance = BigDecimal.valueOf(0);
        //查询支付方式列表
        List<OrderPayWay> orderPayWays = orderPayWayService.list(Wrappers.<OrderPayWay>query().eq("opw_state", 1));
        //并且把 支付标识作为key值，type作为value 方便获取，避免多次查询数据库
        Map<String, Integer> payMap = orderPayWays.stream().collect(Collectors.toMap(OrderPayWay::getPayId, OrderPayWay::getType));
        //如果支付方式不为空
        if(!ComUtil.isEmpty(orderDetailPays)){
            //遍历支付方式
            for (OrderDetailPay orderDetailPay : orderDetailPays){
                orderDetailPay.setOrderDetailId(orderDetail.getId());
                //如果有划卡支付的,进行扣除操作(类似于库存扣除操作)
                if(orderDetailPay.getPayWayId().equals("card")){
                    //划卡操作
                    cardConsumption(orderDetail, orderDetailPay, cardConsumption);
                //非划卡操作
                }else {
                    //获取出支付方式
                    Integer type = payMap.get(orderDetailPay.getPayWayId());
                    //卡耗
                    if(type.equals(1)){
                        cardConsumption = cardConsumption.add(orderDetailPay.getPayMoney());
                    }
                    //业绩
                    if(type.equals(2)){
                        //业绩相加
                        performance = performance.add(orderDetailPay.getPayMoney());
                    }
                    orderDetailPay.setPayType(type);
                    //金额
                    orderDetailPay.setPayMoneyType(1);

                }
                //单位
                unit = orderDetailPay.getPayMoneyType();
                //订单总金额
                money.add(performance);
            }
            orderDetailPayService.saveBatch(orderDetailPays);
        }

        //如果服务员工不为空
        if(!ComUtil.isEmpty(serviceStaffs)){
            //查询提成信息
            List<ConditionContentCommission> conditionContentCommissions = conditionContentCommissionMapper
                    .selectList(Wrappers.<ConditionContentCommission>query()
                            .eq("ccc_content_id", orderDetail.getContentId())
                            .in("ccc_position_id", serviceStaffs.stream().map(OrderDetailServiceStaff::getEmployeePositionId).collect(Collectors.toList())));
            Map<String, ConditionContentCommission> immutableMap = null;
            if(!ComUtil.isEmpty(conditionContentCommissions)){
                immutableMap = Maps.uniqueIndex(conditionContentCommissions,
                        ConditionContentCommission::getPositionId);
            }
            //护理记录
            for(OrderDetailServiceStaff serviceStaff:serviceStaffs){
                serviceStaff.setOrderDetailId(orderDetail.getId());
                serviceStaff.setPerformance(performance);
                serviceStaff.setCardExpend(cardConsumption);
                serviceStaff.setCardExpendUnit(unit);
                //设置员工提成
                setStaffCommission(orderDetail, serviceStaff, immutableMap);
            }
            //存储
            orderDetailServiceStaffService.saveBatch(serviceStaffs);
        }
    }

    /**
     * 设置订单基础信息
     * @param order
     */
    private void setOrderBasicsInfo(Order order) {
        //设置店铺信息
        shopSetterUtil.shopSetter(order, order.getShopId());
        //设置开单时间
        order.setKdTime(LocalDateTime.now());
        //如果已付款则设置当前时间为收银时间
        if(order.getStatus().equals(1)){
            order.setCollectMoneyTime(LocalDateTime.now());
        }
        //生成订单编号
        order.setOrderNumber(orderUtil.generateOrderSn());
        //如果房间标识不为空设置房间信息
        if(!ComUtil.isEmpty(order.getRoomId())){
            order.setRoomName(roomMapper.selectById(order.getRoomId()).getName());
        }
    }

    /**
     * 设置卡片的开始日期结束日期
     * @param memberCard
     * @param conditionCard
     */
    private void setCardDateRange(MemberCard memberCard, ConditionCardVO conditionCard) {
        Integer startDateType = conditionCard.getStartDateType();
        //以购卡时间为开始日期
        if(startDateType.equals(1)){
            memberCard.setCardStartDate(LocalDate.now());
        //以指定日期为开始日期
        }else if(startDateType.equals(3)){
            memberCard.setCardStartDate(conditionCard.getStartDate());
        }
        Integer endDateType = conditionCard.getEndDateType();
        if(endDateType.equals(2)){
            //指定日期过期
            memberCard.setCardExpireDateType(2);
            //判断结束日期类型，如果是固定时长需要特殊处理
            Integer unit = conditionCard.getEndDateDurationUnit();
            Integer duration = conditionCard.getEndDateDuration();
            //根据长度和单位设置到期时间
            switch (unit){
                //1为年
                case 1:
                    memberCard.setCardExpireDate(LocalDate.now().plusYears(duration));
                    break;
                //2为月
                case 2:
                    memberCard.setCardExpireDate(LocalDate.now().plusMonths(duration));
                    break;
                //3为日
                case 3:
                    memberCard.setCardExpireDate(LocalDate.now().plusDays(duration));
                    break;
            }
        }else if(endDateType.equals(1)){
            //永久有效
            memberCard.setCardExpireDateType(1);
        }
    }

    //设置提成
    private void setStaffCommission(OrderDetail orderDetail, OrderDetailServiceStaff serviceStaff, Map<String, ConditionContentCommission> immutableMap) {
        if(ComUtil.isEmpty(immutableMap)){
            return;
        }
        //查询提成信息
        ConditionContentCommission conditionContentCommission = immutableMap.get(serviceStaff.getEmployeePositionId());
        if(!ComUtil.isEmpty(conditionContentCommission) && !ComUtil.isEmpty(conditionContentCommission.getCommission())){
            Integer commissionType = conditionContentCommission.getCommissionType();
            if(!ComUtil.isEmpty(commissionType)){
                serviceStaff.setCommission(BigDecimal.valueOf(0));
            }
            //按百分比点数走
            if(commissionType.equals(1)){
                //四舍五入保留两位小数
                serviceStaff.setCommission(orderDetail.getContentPrice().multiply(conditionContentCommission.getCommission()
                        .divide(BigDecimal.valueOf(100))).setScale(2, BigDecimal.ROUND_HALF_UP));
            //按提成价走
            }else {
                serviceStaff.setCommission(conditionContentCommission.getCommission());
            }
        }
    }

    //卡耗操作，卡内容的扣除操作
    private void cardConsumption(OrderDetail orderDetail, OrderDetailPay orderDetailPay, BigDecimal cardConsumption) {
        //卡耗操作
        orderDetailPay.setPayType(1);
        String memberCardId = orderDetailPay.getMemberCardId();
        //查出客户卡片信息
        MemberCardVO memberCardVo = memberCardMapper.selectMemberCardById(memberCardId);
        //获取客户卡片内容里的详情
        List<MemberCardDetail> memberCardDetails = memberCardVo.getMemberCardDetailList();
        //循环卡内的内容
        for (MemberCardDetail memberCardDetail:memberCardDetails){
            //如果订单中的项目在卡内容含有，则进行扣除操作(相当于扣库存操作)
            if(!ComUtil.isEmpty(orderDetail.getContentId()) &&
                    orderDetail.getContentId().equals(memberCardDetail.getContentId())){
                //需要判断内容类型，来确定扣除方式
                Integer contentType = memberCardDetail.getContentType();
                if(!ComUtil.isEmpty(contentType)){
                    //获取内容
                    BigDecimal payContent = orderDetailPay.getPayMoney();
                    //卡耗记录
                    cardConsumption = cardConsumption.add(payContent);
                    //如果是储值卡或者总次卡计算卡余,并且为卡内容中的项目
                    if((memberCardVo.getCardTypeWay().equals(1) || memberCardVo.getCardTypeWay().equals(2))
                            && memberCardDetail.getDetailType().equals(1)){
                        //剩余
                        BigDecimal cardSurplus = memberCardVo.getCardSurplus();
                        //1是钱，2是次数
                        orderDetailPay.setPayMoneyType(memberCardVo.getCardTypeWay().equals(1)?1:2);
                        //扣款前
                        orderDetailPay.setPayBefore(cardSurplus);
                        memberCardVo.setCardSurplus(cardSurplus.subtract(payContent));
                        //扣款后
                        orderDetailPay.setPayLater(memberCardVo.getCardSurplus());
                        MemberCard memberCard = new MemberCard();
                        //属性拷贝
                        BeanUtils.copyProperties(memberCardVo, memberCard);
                        //修改库存
                        memberCardMapper.updateById(memberCard);
                    }else {
                        //次卡
                        if(contentType.equals(3)){
                            //分次卡，在单个内容做减法操作
                            orderDetailPay.setPayMoneyType(2);
                            orderDetailPay.setPayBefore(memberCardDetail.getContentSurplus());
                            memberCardDetail.setContentSurplus(memberCardDetail.getContentSurplus().subtract(payContent));
                            orderDetailPay.setPayLater(memberCardDetail.getContentSurplus());
                        }else {
                            //类型是金额
                            orderDetailPay.setPayMoneyType(1);
                        }
                    }
                }
            }
        }
    }
}
