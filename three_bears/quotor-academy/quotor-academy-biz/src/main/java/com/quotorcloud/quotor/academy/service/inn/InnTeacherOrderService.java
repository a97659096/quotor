package com.quotorcloud.quotor.academy.service.inn;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacherOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.inn.InnTeacherAppointVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 下店订单表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
public interface InnTeacherOrderService extends IService<InnTeacherOrder> {

    String saveInnTeacherOrderNATIVE(InnTeacherOrder innTeacherOrder, HttpServletRequest request);

    String saveInnTeacherOrderJSAPI(InnTeacherOrder innTeacherOrder, HttpServletRequest request);

    IPage<InnTeacherAppointVO> listTeacherAppoint(Page<InnTeacherAppointVO> page, InnTeacherOrder innTeacherOrder);

}
