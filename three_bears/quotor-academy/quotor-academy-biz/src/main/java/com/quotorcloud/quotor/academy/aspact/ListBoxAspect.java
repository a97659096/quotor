//
//package com.quotorcloud.quotor.academy.aspact;
//
//import com.quotorcloud.quotor.academy.annotation.ListBox;
//import com.quotorcloud.quotor.common.core.util.ComUtil;
//import com.quotorcloud.quotor.common.core.util.MethodUtil;
//import com.quotorcloud.quotor.common.core.util.SpringContextHolder;
//import com.quotorcloud.quotor.academy.event.ListBoxEvent;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
///**
// * 操作日志使用spring event异步入库
// *
// */
//@Aspect
//@Slf4j
//@Component
//public class ListBoxAspect {
//
//	@Around("@annotation(listBox)")
//	@SneakyThrows
//	public Object around(ProceedingJoinPoint point, ListBox listBox) {
//		String strClassName = point.getTarget().getClass().getName();
//		String strMethodName = point.getSignature().getName();
//		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
//		String name = (String) MethodUtil.getGetMethod(point.getTarget().getClass(), strMethodName);
//		if(ComUtil.isEmpty(name)){
//			return null;
//		}
//		com.quotorcloud.quotor.academy.api.entity.ListBox listBox1 = com.quotorcloud.quotor.academy.api.
//				entity.ListBox.builder().name(name)
//				.module(listBox.module()).tag(listBox.tag()).build();
//		// 发送异步日志事件
//		Long startTime = System.currentTimeMillis();
//		Object obj = point.proceed();
//		Long endTime = System.currentTimeMillis();
//		SpringContextHolder.publishEvent(new ListBoxEvent(listBox1));
//		return obj;
//	}
//
//}
