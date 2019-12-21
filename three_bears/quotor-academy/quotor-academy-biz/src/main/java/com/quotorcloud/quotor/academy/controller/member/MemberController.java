package com.quotorcloud.quotor.academy.controller.member;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.member.MemberDTO;
import com.quotorcloud.quotor.academy.service.employee.EmployeeService;
import com.quotorcloud.quotor.academy.service.member.MemberService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 客户信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public R saveMember(MemberDTO memberDTO){
        return R.ok(memberService.saveMember(memberDTO));
    }

    @PutMapping
    public R updateMember(MemberDTO memberDTO){
        return R.ok(memberService.updateMember(memberDTO));
    }

    @DeleteMapping("{id}")
    public R deleteMember(@PathVariable String id){
        return R.ok(memberService.removeMember(id));
    }

    @GetMapping("list")
    public R listMember(Page page, MemberDTO memberDTO){
        return R.ok(memberService.listMember(page, memberDTO));
    }

    @GetMapping("list/{id}")
    public R selectMemberById(@PathVariable String id){
        return R.ok(memberService.getMemberById(id));
    }

    @GetMapping("list/box")
    public R listBoxMember(String shopId){
        List<JSONObject> employeeListBox = employeeService.selectEmployeeListBox(shopId);
        List<JSONObject>  memberListBox = memberService.selectMemberListBox(shopId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emp", employeeListBox);
        jsonObject.put("mem", memberListBox);
        return R.ok(jsonObject);
    }

}
