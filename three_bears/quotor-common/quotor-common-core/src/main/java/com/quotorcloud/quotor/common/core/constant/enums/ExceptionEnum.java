package com.quotorcloud.quotor.common.core.constant.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    SAVE_FILE_FAILED(101,"保存文件失败请重新添加！"),
    SAVE_TEACHER_FAILED(102, "新增讲师信息失败！"),
    UPDATE_TEACHER_FAILED(103, "修改讲师信息失败！"),
    DELETE_TEACHER_FAILED(104, "删除讲师信息失败！"),
    NOT_FIND_ID(105, "操作失败，未接收到唯一标识！"),
    DELETE_IMG_FAILED(106, "删除图片失败，请重新操作！"),
    DATE_RANGE_ERROR(107, "上课日期格式错误！"),
    SAVE_COURSE_FAILED(108, "新增课程失败，请重新添加！"),
    DELETE_COURSE_FAILED(109, "刪除课程失败，请重新操作！"),
    SAVE_EMPLOYEE_FAILED(110, "新增员工信息失败，请重新操作！"),
    NOT_FIND_TEACHER(111,"未找到所选讲师，请重新选择！"),
    DELETE_EMPLOYEE_FAILED(112, "删除员工失败，请重新操作"),
    PRODUCT_INVENTORY_NO_HAVE(113, "出库失败，商品数量不足"),
    NOT_FIND_PRODUCT(114, "未找到所选商品，请重新选择"),
    ENABLE_BANNER_OVER(115, "新增banner失败，店铺启用banner超出六张！"),
    NOT_FIND_ROLE(116, "未找到角色标识，请重新操作！"),
    NOT_FIND_DEPT(203, "未找到用户所属店铺信息，请联系管理员进行操作！")
    ;

    private Integer code;

    private String message;
}
