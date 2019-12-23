package com.quotorcloud.quotor.academy.api.dto.invitejob;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;


@Data
public class InviteJobDTO {

    private String id;

    /**
     * 岗位标识
     */
    private String positionId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 福利
     */
    private String welfare;

    /**
     * 工资（日新） 始值
     */
    private BigDecimal salaryStart;

    /**
     * 工资 终值
     */
    private BigDecimal salaryEnd;

    /**
     * 地址
     */
    private String address;

    /**
     * 所属店铺标识
     */
    private String shopId;

    /**
     * 招聘状态，1发布，2下架，3待发布
     */
    private Integer status;

    /**
     * 岗位详情
     */
    private String positionDetail;

    /**
     * 技能要求
     */
    private String skillRequire;

    /**
     * 技能特长
     */
    private String skillSuper;

    /**
     * 个人详情
     */
    private String ownerDetail;

    /**
     * 开始日期
     */
    private String dateRange;

    private String start;

    private String end;

    /**
     * 发布时间
     */
    private String issueTime;

    /**
     * 类型，1招聘，2求职
     */
    private Integer type;

    /**
     * 下店人员标识
     */
    private String xdrId;

    /**
     * 下店人员名称
     */
    private String xdrName;

    private MultipartFile[] img;

    private List<String> imgString;
}
