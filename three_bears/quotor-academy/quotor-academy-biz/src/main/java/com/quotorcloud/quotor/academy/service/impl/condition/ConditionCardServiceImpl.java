package com.quotorcloud.quotor.academy.service.impl.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.condition.ConditionCardDTO;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCard;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardDetail;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionCardType;
import com.quotorcloud.quotor.academy.api.vo.condition.ConditionCardVO;
import com.quotorcloud.quotor.academy.log.annotation.OperationLog;
import com.quotorcloud.quotor.academy.log.enums.OperationType;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionCardMapper;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionCardTypeMapper;
import com.quotorcloud.quotor.academy.service.condition.ConditionCardDetailService;
import com.quotorcloud.quotor.academy.service.condition.ConditionCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.service.condition.ConditionCategoryService;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.*;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 卡片信息 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@Service
@Slf4j
public class ConditionCardServiceImpl extends ServiceImpl<ConditionCardMapper, ConditionCard> implements ConditionCardService {

    @Autowired
    private ConditionCardMapper conditionCardMapper;

    @Autowired
    private ConditionCardDetailService conditionCardDetailService;

    @Autowired
    private ConditionCategoryService conditionCategoryService;

    @Autowired
    private ConditionCardTypeMapper conditionCardTypeMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    /**
     * 新增
     * @param conditionCardDTO
     * @return
     */
    @Override
    @Transactional
    @OperationLog(name = "新增卡片", contentType = 2, operatorRef = 0, operatorObj = 1, table = "bear_condition_card", type = OperationType.ADD, cloum = "c_name")
    public Boolean saveConditionCard(QuotorUser user, ConditionCardDTO conditionCardDTO) {
        shopSetterUtil.shopSetter(conditionCardDTO, conditionCardDTO.getShopId());
        ConditionCard conditionCard = getConditionCard(conditionCardDTO);
        conditionCard.setDelState(CommonConstants.STATUS_NORMAL);
        conditionCardMapper.insert(conditionCard);
        List<ConditionCardDetail> cardContent = getConditionCardDetails(conditionCardDTO, conditionCard);
        //插入操作
        if(!ComUtil.isEmpty(cardContent)){
            conditionCardDetailService.saveBatch(cardContent);
        }
        return Boolean.TRUE;
    }

    @Override
    public IPage<ConditionCardVO> selectConditionCard(Page page, ConditionCardDTO conditionCardDTO) {
        //得到id集合，方便查询子级下的内容
        if(!ComUtil.isEmpty(conditionCardDTO.getCategoryId())) {
            conditionCardDTO.setCategoryIds(conditionCategoryService.findCategoryIds(conditionCardDTO.getCategoryId()));
        }
        //分页查询
        IPage<ConditionCardVO> conditionCardVOS = conditionCardMapper.selectCardPage(page, conditionCardDTO);
        for (ConditionCardVO conditionCard : conditionCardVOS.getRecords()){
            mapCardVO(conditionCard);
        }
        return conditionCardVOS;
    }

    @Override
    @OperationLog(name = "修改卡片", contentType = 2, operatorRef = 0, operatorObj = 1, idRef = 2, table = "bear_condition_card",
            type = OperationType.UPDATE, idField = "c_id")
    public Boolean updateConditionCard(QuotorUser user,ConditionCardDTO conditionCardDTO, String id) {
        //把有变化的类型放到集合里批量删除
        List<Integer> types = new ArrayList<>();
        if(!ComUtil.isEmpty(conditionCardDTO.getCardContent())){
            types.add(CommonConstants.CONDITION_CARD_CONTENT);
        }
        if(!ComUtil.isEmpty(conditionCardDTO.getBuyCardGive())){
            types.add(CommonConstants.CONDITION_BUY_CARD_GIVE);
        }
        if(!ComUtil.isEmpty(conditionCardDTO.getTopUpGive())){
            types.add(CommonConstants.CONDITION_TOPUP_GIVE);
        }
        conditionCardDetailService.remove(new QueryWrapper<ConditionCardDetail>()
                .eq("cd_card_id", conditionCardDTO.getId())
                .in("cd_detail_type", types));
        //修改卡片信息
        ConditionCard conditionCard = getConditionCard(conditionCardDTO);
        conditionCardMapper.updateById(conditionCard);
        //修改卡片详情
        List<ConditionCardDetail> conditionCardDetails = getConditionCardDetails(conditionCardDTO, conditionCard);
        conditionCardDetailService.saveBatch(conditionCardDetails);
        return Boolean.TRUE;
    }

    @Override
    @OperationLog(name = "删除会员", contentType = 2, operatorRef = 0, idRef = 1, table = "bear_condition_card",
            type = OperationType.DELETE, cloum = "c_name", idField = "c_id")
    public Boolean removeConditionCard(QuotorUser user, String id) {
        conditionCardMapper.deleteById(id);
        return Boolean.TRUE;
    }

    private ConditionCard getConditionCard(ConditionCardDTO conditionCardDTO) {
        //首先保存除卡内容，购卡赠送，充值赠送外的内容
        ConditionCard conditionCard = new ConditionCard();
        //拷贝属性
        BeanUtils.copyProperties(conditionCardDTO, conditionCard);

        if(!ComUtil.isEmpty(conditionCardDTO.getTemplateTypeId())){
            ConditionCardType conditionCardType = conditionCardTypeMapper.selectById(conditionCardDTO.getTemplateTypeId());
            conditionCard.setTemplateTypeName(conditionCardType.getName());
            conditionCard.setCardType(conditionCardType.getType());
        }
        return conditionCard;
    }

    private List<ConditionCardDetail> getConditionCardDetails(ConditionCardDTO conditionCardDTO, ConditionCard conditionCard) {
        //处理卡内容、购卡赠送
        List<ConditionCardDetail> cardContent = conditionCardDTO.getCardContent();

        //设置type为1,1为卡内容
        if(!ComUtil.isEmpty(cardContent)) {
            cardContent.forEach(content -> {
                content.setDetailType(CommonConstants.CONDITION_CARD_CONTENT);
                content.setCardId(conditionCard.getId());
            });
        }
        //如果是总次卡，name所有的内容次数都应该是一样的，如果不一样进行回滚
//        if(conditionCard.getCardType().equals(CommonConstants.CARD_TYPE_TOTAL)){
//            Set<BigDecimal> set = cardContent.stream().map(ConditionCardDetail::getContent).collect(Collectors.toSet());
//            if(set.size() > 1){
//                log.error("总次卡，存在次数不一致的问题,进行回滚。。。");
//                throw new RuntimeException();
//            }
//        }

        List<ConditionCardDetail> buyCardGive = conditionCardDTO.getBuyCardGive();
        //设置type为2，2为购卡赠送
        if(!ComUtil.isEmpty(buyCardGive)) {
            buyCardGive.forEach(cardgive -> {
                cardgive.setDetailType(CommonConstants.CONDITION_BUY_CARD_GIVE);
                cardgive.setCardId(conditionCard.getId());
            });
            cardContent.addAll(buyCardGive);
        }
        //把两个集合add到一个集合里，通过type区分开来
        //最后处理充值赠送内容
//        List<ConditionCardDetail> topUpGive = conditionCardDTO.getTopUpGive();
//        topUpGive.forEach(give -> {
//            give.setDetailType(CommonConstants.CONDITION_TOPUP_GIVE);
//            give.setCardId(conditionCard.getId());
//        });
//        cardContent.addAll(topUpGive);
        return cardContent;
    }

    private void mapCardVO(ConditionCardVO conditionCard) {
        List<ConditionCardDetail> conditionCardDetails = conditionCard.getConditionCardDetails();
        //根据详情类型分组
        Map<Integer, List<ConditionCardDetail>> collect = conditionCardDetails.stream()
                .collect(Collectors.groupingBy(ConditionCardDetail::getDetailType));
        //卡片内容
        List<ConditionCardDetail> cardContent = collect.get(CommonConstants.CONDITION_CARD_CONTENT);
        if(!ComUtil.isEmpty(cardContent)){
            conditionCard.setCardContent(cardContent);
        }
        //购卡赠送
        List<ConditionCardDetail> buyCardGive = collect.get(CommonConstants.CONDITION_BUY_CARD_GIVE);
        if(!ComUtil.isEmpty(buyCardGive)){
            conditionCard.setBuyCardGive(buyCardGive);
        }
        //充值赠送
//        List<ConditionCardDetail> topUpGive = collect.get(CommonConstants.CONDITION_TOPUP_GIVE);
//        if(!ComUtil.isEmpty(topUpGive)){
//            Map<BigDecimal, List<ConditionCardDetail>> decimalListMap = topUpGive.stream().collect(Collectors.groupingBy(ConditionCardDetail::getCdReachMoney));
//            List<ConditionCardDetailDTO> conditionCardDetailDTOS = new LinkedList<>();
//            for (BigDecimal decimal : decimalListMap.keySet()){
//                ConditionCardDetailDTO conditionCardDetailDTO = new ConditionCardDetailDTO();
//                conditionCardDetailDTO.setKey(decimal);
//                conditionCardDetailDTO.setValue(decimalListMap.get(decimal));
//                conditionCardDetailDTOS.add(conditionCardDetailDTO);
//            }
//            //按充值金额进行排序
//            Collections.sort(conditionCardDetailDTOS);
//            conditionCard.setTopUpGive(conditionCardDetailDTOS);
//        }
    }
}
