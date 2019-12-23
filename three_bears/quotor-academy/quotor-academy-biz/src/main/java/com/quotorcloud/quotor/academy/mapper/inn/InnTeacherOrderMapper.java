package com.quotorcloud.quotor.academy.mapper.inn;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacherOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.inn.InnTeacherAppointVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 下店订单表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
public interface InnTeacherOrderMapper extends BaseMapper<InnTeacherOrder> {

    IPage<InnTeacherAppointVO> listAppointTeacherPage(Page<InnTeacherAppointVO> page,
                                                      @Param("inn") InnTeacherOrder innTeacherOrder);
}
