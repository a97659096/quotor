package com.quotorcloud.quotor.academy.api.vo.course;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseVO {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程内容
     */
    private String content;

    private String intro;

    /**
     * 周期
     */
    private String period;

    /**
     * 课程图片
     */
    private String img;

    /**
     * 课程讲师标识
     */
    private String teacherId;

    /**
     * 课程讲师名称
     */
    private String teacherName;

    /**
     * 课程总票数
     */
    private Integer totalPoll;

    /**
     * 课程剩余票数
     */
    private Integer surplusPoll;

    /**
     * 加盟商价格
     */
    private BigDecimal joinShopPrice;

    /**
     * 学员价格
     */
    private BigDecimal studentPrice;

    private List<Long> dateRange;

    /**
     * 课程类型：1内部课程，2外部课程，3通用课程
     */
    private Integer type;

    /**
     * 课程状态：1已发布，2待发布，3已下架
     */
    private Integer status;

    private String schoolTime;
}
