package com.quotorcloud.quotor.academy.api.dto.employee;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
public class EmployeeDTO {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，1男，2女
     */
    private Integer sex;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 入职时间
     */
    private String joinDate;

    /**
     * 员工花名
     */
    private String alias;

    /**
     * 所属门店标识
     */
    private String shopId;

    /**
     * 所属门店名称
     */
    private String shopName;

    /**
     * 员工职位标识
     */
    private String positionId;

    /**
     * 员工职位名称
     */
    private String positionName;

    /**
     * 员工头衔标识
     */
    private String headTitleId;

    /**
     * 员工头衔名称
     */
    private String headTitleName;

    /**
     * 员工工号
     */
    private String jobNumber;

    /**
     * 员工微信号
     */
    private String wechatNumber;

    /**
     * 员工身份证号
     */
    private String identityCard;

    /**
     * 头像
     */
    private MultipartFile headImg;

    private String headImgString;

    /**
     * 住址
     */
    private String address;

    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    private String emerPhone;

    /**
     * 个人技能
     */
    private String skill;

    /**
     * 个人描述
     */
    private String description;

    /**
     * 员工作品集
     */
    private MultipartFile[] works;

    private List<String> worksString;

    /**
     * 在职状态：1在职，2离职
     */
    private Integer workState;

    /**
     * 是否可预约，1可以0不可以
     */
    private Integer subscribe;

    private String userId;

    /**
     * 预约排序
     */
    private Integer sort;

    private String remark;

    private Integer pageNo;

    private Integer pageSize;

    private String dateRange;

    private String start;

    private String end;

    private String date;

    private Integer performanceSort;

    private Integer cardConsumeSort;

    private Integer commissionSort;

    private Integer contentType;

    private String appointDate;

    /**
     * 1周2月3年
     */
    private Integer dateType;
}
