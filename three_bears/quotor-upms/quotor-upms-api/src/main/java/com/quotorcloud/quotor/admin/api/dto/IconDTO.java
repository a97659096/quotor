package com.quotorcloud.quotor.admin.api.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class IconDTO {

    private String id;

    /**
     * 图标
     */
    private MultipartFile icon;

    private String iconString;

    /**
     * 图标描述
     */
    private String description;

    /**
     * 图标状态，1启用，2停用
     */
    private Integer state;

    private Integer pageNo;

    private Integer pageSize;
}
