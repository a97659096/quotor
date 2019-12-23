package com.quotorcloud.quotor.academy.service.inn;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.inn.InnTeacherDTO;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 下店老师信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
public interface InnTeacherService extends IService<InnTeacher> {

    Boolean saveInnTeacher(InnTeacherDTO innTeacherDTO);

    Boolean updateInnTeacher(InnTeacherDTO innTeacherDTO);

    Boolean removeInnTeacher(String id);

    IPage<InnTeacher> listInnTeacher(Page<InnTeacher> page, InnTeacherDTO innTeacherDTO);

    InnTeacher selectInnTeacherById(String id);

}
