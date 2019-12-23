package com.quotorcloud.quotor.academy.log.annotation;

import com.quotorcloud.quotor.academy.log.enums.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标注需要进行操作日志的服务函数上
 * @author taoken
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {
	/** 业务名 */
	String name();
	/** 表名 */
	String table();
	/** id 在函数的字段名 */
	int idRef() default -1; 
	/** 需要记录的字段 */
	String[] cloum() default {};
	/** 操作类型 */
	OperationType type();

	/**
	 *  内容类型，1会员操作，2品项操作，3课程操作，4讲师操作，5员工操作
	 */
	int contentType();
	/** 操作人 id 在函数的字段名*/
	int operatorRef();
	/** 操作人名称 在函数的字段名 */
	int operatorObj() default -1;

	String idField() default "";
	//	int operatorNameRef();
}
