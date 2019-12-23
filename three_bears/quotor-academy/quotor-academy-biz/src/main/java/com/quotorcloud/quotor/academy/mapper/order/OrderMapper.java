package com.quotorcloud.quotor.academy.mapper.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.order.OrderDTO;
import com.quotorcloud.quotor.academy.api.entity.order.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.order.OrderWebVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据会员标识集合查询订单信息
     * @param memberIds
     * @return
     */
    List<OrderWebVO> selectOrderWebVO(@Param("order") OrderDTO orderDTO);

    IPage<OrderWebVO> selectOrderWebVO(Page page, @Param("order") OrderDTO orderDTO);

    /**
     * 查询到店频次大于times的会员标识
     * @param times
     * @return
     */
    List<String> selectMemberReachShopFrequency(@Param("times") Integer times);

    /**
     * 查询天数之内未到店的会员标识
     * @param days
     * @return
     */
    List<String> selectNotReachShopDays(@Param("days") Integer days);

    /**
     * 查询到客数量
     * @param shopId
     * @return
     */
    Integer selectToStoreGuest(String shopId);

}
