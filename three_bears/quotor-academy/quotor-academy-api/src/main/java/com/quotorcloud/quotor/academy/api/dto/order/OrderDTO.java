package com.quotorcloud.quotor.academy.api.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private String memberId;

    private List<String> memberIds;

    private String shopId;

    private Integer payType;
}
