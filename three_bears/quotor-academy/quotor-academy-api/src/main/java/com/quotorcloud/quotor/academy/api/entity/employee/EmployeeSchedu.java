package com.quotorcloud.quotor.academy.api.entity.employee;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工排班信息表
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bear_employee_schedu")
public class EmployeeSchedu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "es_id", type = IdType.UUID)
    private String esId;

    /**
     * 员工标识
     */
    private String esEmployeeId;

    private String esEmployeeName;

    /**
     * 排班日期
     */
    private LocalDate esStartDate;

    private LocalDate esEndDate;

    /**
     * 排班所属班次标识
     */
    private String esClassId;

    private String esShopId;

    /**
     * 创建时间
     */
    private LocalDateTime esGmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime esGmtModified;


}
