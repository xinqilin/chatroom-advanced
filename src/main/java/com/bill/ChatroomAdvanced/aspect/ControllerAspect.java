package com.bill.ChatroomAdvanced.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class ControllerAspect {

	@Around("execution(* com.bill.ChatroomAdvanced.controller.*.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		log.info(String.format("before method{%s} Object{%s}", joinPoint.getSignature(), joinPoint.getTarget()));
		Object obj = joinPoint.proceed();
		log.info(String.format("after used %d ms", ((System.currentTimeMillis() - startTime))));
		return obj;
	}
}
