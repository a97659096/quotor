package com.quotorcloud.quotor.academy.mapper.course;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.course.CourseOrderDTO;
import com.quotorcloud.quotor.academy.api.entity.course.CourseOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.course.CourseAppointmentVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程订单表（课程预约） Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-05
 */
public interface CourseOrderMapper extends BaseMapper<CourseOrder> {

    IPage<CourseAppointmentVO> selectCourseAppointPage(Page<CourseOrder> page, @Param("co")CourseOrderDTO courseOrderDTO);

    IPage<CourseOrder> listCourseOrderPage(Page<CourseOrder> page, @Param("co") CourseOrderDTO courseOrderDTO);

}
