package com.quotorcloud.quotor.academy.api.dto.member;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemberLogDTO {

    private String id;

    /**
     * 员工标识
     */
    private String employeeId;

    //员工姓名
    private String employeeName;

    //服务内容
    private String serviceContent;

    /**
     * 会员标识
     */
    private String memberId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 相关图片
     */
    private MultipartFile[] img;

    private List<String> imgString;

}
