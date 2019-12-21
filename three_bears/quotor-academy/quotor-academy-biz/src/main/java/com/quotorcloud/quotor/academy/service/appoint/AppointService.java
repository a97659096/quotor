package com.quotorcloud.quotor.academy.service.appoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointDTO;
import com.quotorcloud.quotor.academy.api.dto.appoint.AppointEmployeeDTO;
import com.quotorcloud.quotor.academy.api.entity.appoint.Appoint;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.appoint.AppointVO;

import java.util.List;

/**
 * <p>
 * 预约信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-21
 */
public interface AppointService extends IService<Appoint> {

    Boolean saveAppoint(AppointDTO appointDTO);

    Boolean updateAppoint(AppointDTO appointDTO);

    Boolean cancelAppoint(String id);

    List<AppointEmployeeDTO> formAppoint(AppointVO appointVO);

    IPage<AppointVO> listAppoint(Page<AppointVO> page, AppointVO appointVO);

    AppointDTO listAppointById(String id, String appointId);

}
