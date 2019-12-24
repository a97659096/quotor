package com.quotorcloud.quotor.academy.mapper.order;

import com.quotorcloud.quotor.academy.api.dto.expend.ExpendDTO;
import com.quotorcloud.quotor.academy.api.dto.order.OrderIncomeDTO;
import com.quotorcloud.quotor.academy.api.entity.expend.Expend;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetailPay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quotorcloud.quotor.academy.api.vo.order.OrderIncomeVO;
import com.quotorcloud.quotor.academy.api.vo.order.OrderIncomesVO;
import com.quotorcloud.quotor.common.core.util.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * <p>
 * 订单付款详情 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
public interface OrderDetailPayMapper extends BaseMapper<OrderDetailPay> {

    List<OrderDetailPay> listOrderDetailPay(@Param("pay") OrderDetailPay orderDetailPay);

    /**
     * 根据类别查询分组计算金额
     * @param shopId 所属店铺标识
     * @return
     */
    List<OrderIncomeVO> listOrderIncomeVO(@Param("orderpay") OrderDetailPay orderDetailPay);

    /**
     *查询收入记录
     * @param
     * @return
     */
    List<OrderIncomesVO> listOrderIncomesVO(@Param("orderIncomesVO") OrderIncomesVO orderIncomesVO);

    /**
     * 查询最近收入
     * @param orderIncomeDTO
     * @return
     */
    List<OrderIncomeDTO> selectOrderRecently(@Param("orderIncomeDTO") OrderIncomeDTO orderIncomeDTO);


}
