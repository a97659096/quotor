package com.quotorcloud.quotor.academy.api.entity.course;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程评价
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_course_evaluate")
public class CourseEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 被评论标识
     */
    private String courseId;

    /**
     * 评价分数
     */
    private Integer score;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人
     */
    private String userId;

    /**
     * 评论类型，1对课程，2对讲师，3对学员
     */
    private Integer type;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

}
