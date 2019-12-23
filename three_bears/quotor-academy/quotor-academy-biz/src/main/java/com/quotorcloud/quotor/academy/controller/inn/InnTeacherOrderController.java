package com.quotorcloud.quotor.academy.controller.inn;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacherOrder;
import com.quotorcloud.quotor.academy.api.vo.inn.InnTeacherAppointVO;
import com.quotorcloud.quotor.academy.service.inn.InnTeacherOrderService;
import com.quotorcloud.quotor.academy.util.OrderUtil;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 下店订单表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/inn/teacher/order")
public class InnTeacherOrderController {

    @Autowired
    private InnTeacherOrderService innTeacherOrderService;

    @Autowired
    private OrderUtil orderUtil;

    @PostMapping("native/save")
    public void nativeSaveOrder(InnTeacherOrder innTeacherOrder, HttpServletRequest request,
                                HttpServletResponse response){
        String codeUrl = innTeacherOrderService.saveInnTeacherOrderNATIVE(innTeacherOrder, request);
        if(ComUtil.isEmpty(codeUrl)){
            throw new NullPointerException();
        }
        orderUtil.genertorQRCode(codeUrl, response);
    }

    /**
     * 查询预约列表
     * @param page
     * @param innTeacherOrder
     * @return
     */
    @GetMapping("list")
    public R listAppoint(Page<InnTeacherAppointVO> page, InnTeacherOrder innTeacherOrder){
        return R.ok(innTeacherOrderService.listTeacherAppoint(page, innTeacherOrder));
    }

}
