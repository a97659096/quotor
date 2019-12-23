package com.quotorcloud.quotor.academy.api.dto.banner;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class BannerDTO {

    private String id;

    /**
     * 图片
     */
    private MultipartFile bImg;

    private String bImgString;

    /**
     * 标题
     */
    private String bTitle;

    /**
     * 添加人标识
     */
    private String bAddUserId;

    /**
     * 添加人姓名
     */
    private String bAddUserName;

    /**
     * 店铺标识
     */
    private String bShopId;

    /**
     * 店铺名称
     */
    private String bShopName;

    /**
     * 状态，1启用，2禁用
     */
    private Integer bStatus;

    private String bLink;

    /**
     * 排序字段
     */
    private Integer eSort;

    private Integer pageNo;

    private Integer pageSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
}
