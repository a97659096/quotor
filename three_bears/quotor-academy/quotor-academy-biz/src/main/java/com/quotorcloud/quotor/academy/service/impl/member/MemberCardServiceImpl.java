package com.quotorcloud.quotor.academy.service.impl.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCard;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCardDetail;
import com.quotorcloud.quotor.academy.api.vo.member.MemberCardVO;
import com.quotorcloud.quotor.academy.mapper.member.MemberCardMapper;
import com.quotorcloud.quotor.academy.service.member.MemberCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.admin.api.entity.SysDept;
import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 会员卡信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Service
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCard> implements MemberCardService {

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private RemoteDeptService remoteDeptService;

    /**
     * 查询会员下边的卡详情
     * @param memberCard
     * @return
     */
    @Override
    public List<MemberCardVO> listMemberCardByMemberId(MemberCard memberCard) {
        List<MemberCardVO> memberCardVOS = memberCardMapper.listMemberCard(memberCard);
        countCardSurplus(memberCardVOS);
        return memberCardVOS;
    }

    /**
     * 计算总余量
     * @param memberCard
     * @return
     */
    @Override
    public JSONObject countTotalCardMargin(MemberCard memberCard) {
        List<MemberCardVO> memberCardVOS = memberCardMapper.listMemberCard(memberCard);
        return countCardSurplus(memberCardVOS);
    }

    /**
     * 计算总余量
     * @param memberCard
     * @return
     */
    @Override
    public JSONObject countTotalCardMarginGroupByShopId(MemberCard memberCard) {
        List<MemberCardVO> memberCardVOS = memberCardMapper.listMemberCard(memberCard);

        //根据门店分组
        Map<String, List<MemberCardVO>> memberCardByShopId = memberCardVOS.stream()
                .collect(Collectors.groupingBy(MemberCardVO::getShopId));

        R r = remoteDeptService.listDeptAll();
        List<SysDept> sysDepts = JSON.parseArray(JSON.toJSONString(r.getData()), SysDept.class);
        Map<Integer, SysDept> deptById = Maps.uniqueIndex(sysDepts, SysDept::getDeptId);

        BigDecimal totalCardMarginMoney = new BigDecimal(0);
        Integer totalCardMarginTimes = 0;
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjects = new ArrayList<>();

        for (String shopId:memberCardByShopId.keySet()){
            List<MemberCardVO> memberCards = memberCardByShopId.get(shopId);
//            BigDecimal cardMarginMoney = new BigDecimal(0);
//            Integer cardMarginTimes = 0;
//            for (MemberCardVO memberCardVO : memberCardVOS){
//                if(!ComUtil.isEmpty(memberCardVO.getCardSurplusMoney())){
//                    cardMarginMoney = cardMarginMoney.add(memberCardVO.getCardSurplusMoney());
//                }
//                if(!ComUtil.isEmpty(memberCardVO.getCardSurplusTimes())){
//                    cardMarginTimes = cardMarginTimes + memberCardVO.getCardSurplusTimes();
//                }
//            }
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("cardMarginMoney", cardMarginMoney);
//            jsonObject.put("cardMarginTimes", cardMarginTimes);
            JSONObject jsonObject = countCardSurplus(memberCardVOS);
            jsonObject.put("shopId", shopId);
            jsonObject.put("shopName", deptById.get(Integer.valueOf(shopId)).getName());
            jsonObject.put("shopHeadImg", deptById.get(Integer.valueOf(shopId)).getHeadImg());
            jsonObject.put("cardTimes", memberCards.size());
            totalCardMarginMoney = totalCardMarginMoney.add(new BigDecimal(jsonObject.get("cardMarginMoney").toString()));
            totalCardMarginTimes = totalCardMarginTimes + Integer.valueOf(jsonObject.get("cardMarginTimes").toString());
            jsonObjects.add(jsonObject);
        }
        result.put("totalCardMarginMoney", totalCardMarginMoney);
        result.put("totalCardMarginTimes", totalCardMarginTimes);
        result.put("list", jsonObjects);
        return result;
    }

    /**
     * 计算卡余根据会员分组
     * @param memberCard
     * @return
     */
    @Override
    public JSONObject countTotalCardMarginGroupByMemberId(MemberCard memberCard) {
        List<MemberCardVO> memberCardVOS = memberCardMapper.listMemberCard(memberCard);
        Map<String, List<MemberCardVO>> groupByMemberMap = memberCardVOS.stream().collect(Collectors.groupingBy(MemberCardVO::getMemberId));

        BigDecimal totalCardMarginMoney = new BigDecimal(0);
        Integer totalCardMarginTimes = 0;
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjects = new ArrayList<>();

        for (String key:groupByMemberMap.keySet()){
            List<MemberCardVO> memberCards = groupByMemberMap.get(key);
            //如果不为空
            if(!ComUtil.isEmpty(memberCards)){
                JSONObject jsonObject = countCardSurplus(memberCards);
                jsonObject.put("memberId", memberCards.get(0).getMemberId());
                jsonObject.put("memberName", memberCards.get(0).getMemberName());
                jsonObject.put("memberHeadImg", memberCards.get(0).getMemberHeadImg());
                jsonObject.put("cardTimes", memberCards.size());
                totalCardMarginMoney = totalCardMarginMoney.add(new BigDecimal(jsonObject.get("cardMarginMoney").toString()));
                totalCardMarginTimes = totalCardMarginTimes + Integer.valueOf(jsonObject.get("cardMarginTimes").toString());
                jsonObjects.add(jsonObject);
            }
            result.put("totalCardMarginMoney", totalCardMarginMoney);
            result.put("totalCardMarginTimes", totalCardMarginTimes);
            result.put("list", jsonObjects);
        }
        return result;
    }

    /**
     * 计算卡余（卡余金额，卡余次数）
     * @param memberCardVOS
     */
    private JSONObject countCardSurplus(List<MemberCardVO> memberCardVOS) {
        BigDecimal cardMarginMoney = new BigDecimal(0);
        int cardMarginTimes = 0;

        for (MemberCardVO memberCardVO:memberCardVOS){
            int cardSurplusTimes = 0;
            //为储值卡时
            if(memberCardVO.getCardTypeWay().equals(1)){
                //直接把剩余金额放入
                cardMarginMoney = cardMarginMoney.add(memberCardVO.getCardSurplus());
                memberCardVO.setCardSurplusMoney(memberCardVO.getCardSurplus());
                //为总次或者分次时把次数相加
            }else {
                //总
                cardMarginTimes = cardMarginTimes + Integer.valueOf(memberCardVO.getCardSurplus().toString());

                cardSurplusTimes = cardSurplusTimes + Integer.valueOf(memberCardVO.getCardSurplus().toString());
            }
            //获取卡详情内容
            List<MemberCardDetail> memberCardDetailList = memberCardVO.getMemberCardDetailList();
            for (MemberCardDetail memberCardDetail:memberCardDetailList){
                //为次数时直接加入次数
                if(memberCardDetail.getContentType().equals(3)){
                    cardMarginTimes = cardMarginTimes + Integer
                            .valueOf(memberCardDetail.getContentSurplus().toString());
                    cardSurplusTimes = cardSurplusTimes +
                            Integer.valueOf(memberCardDetail.getContentSurplus().toString());
                }
            }
            //赋值
            memberCardVO.setCardSurplusTimes(cardSurplusTimes);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cardMarginMoney", cardMarginMoney);
        jsonObject.put("cardMarginTimes", cardMarginTimes);
        return jsonObject;
    }
}
