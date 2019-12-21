package com.quotorcloud.quotor.academy.api.vo.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberCountByShopIdVO {

    private String shopId;

    private String shopName;

    private String shopHeadImg;

    private Integer peopleCounts;

}
