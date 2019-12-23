package com.quotorcloud.quotor.admin.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class DeptDTO {

    private Integer deptId;

    /**
     * 店铺头像
     */
    private MultipartFile headImg;

    private String headImgString;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺编号
     */
    private String number;

    /**
     *店铺简介
     */
    private String intro;

    /**
     * 店铺环境
     */
    private MultipartFile[] environment;

    private String environmentString;

    /**
     * 店铺负责人
     */
    private String manager;

    /**
     * 店铺联系电话
     */
    private String phone;

    /**
     * 加入日期
     */
    private LocalDate joinDate;

    /**
     * 到期日期
     */
    private LocalDate expireDate;

    /**
     * 排序
     */
    private Integer sort;


    private Integer messageCount;
}
