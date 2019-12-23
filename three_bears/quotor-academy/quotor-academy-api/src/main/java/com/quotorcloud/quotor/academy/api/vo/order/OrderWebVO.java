package com.quotorcloud.quotor.academy.api.vo.order;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.quotorcloud.quotor.academy.api.entity.order.OrderDetail;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderWebVO implements Comparable<OrderWebVO> {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 流水单号
     */
    private String expendNumber;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 房间标识
     */
    private String roomId;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 会员到店次数
     */
    private Integer memberArrive;

    /**
     * 散客到店次数
     */
    private Integer notMemberArrive;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 客户标识
     */
    private String memberId;

    private String memberName;

    private String memberHeadImg;

    /**
     * 店铺标识
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 开单时间
     */
    private LocalDateTime kdTime;

    /**
     * 收银时间
     */
    private LocalDateTime collectMoneyTime;

    /**
     * 是否是补单
     */
    private Integer replacementOrder;

    /**
     * 订单状态：1已付款，2未付款，3尾款单
     */
    private Integer status;

    /**
     * 订单类型：1综合，2购卡，3卡续充，4还卡升级，5购劵
     */
    private Integer type;

    private List<OrderDetail> orderDetails;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


    @Override
    public int compareTo(OrderWebVO o) {
        return o.getGmtCreate().compareTo(this.getGmtCreate());
    }
}
