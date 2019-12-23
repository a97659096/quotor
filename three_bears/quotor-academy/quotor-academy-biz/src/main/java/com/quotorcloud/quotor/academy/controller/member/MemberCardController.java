package com.quotorcloud.quotor.academy.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.member.MemberCard;
import com.quotorcloud.quotor.academy.service.member.MemberCardService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员卡信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/member/card")
public class MemberCardController {

    @Autowired
    private MemberCardService memberCardService;

    @GetMapping("list")
    public R listMemberCardById(Page page, MemberCard memberCard){
        return null;
    }

    /**
     * 查询会员标识下边的卡详情
     * @param memberCard
     * @return
     */
    @GetMapping("list/memberId")
    public R listMemberCardByMemberId(String memberId){
        MemberCard memberCard = new MemberCard();
        memberCard.setMemberId(memberId);
        return R.ok(memberCardService.listMemberCardByMemberId(memberCard));
    }

}
