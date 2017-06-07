package com.xiaoseller.dw.datasource;


import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

@Aspect
@Order(100)
public class WrDataSourceMethodAspect {

	@Resource
	private WrDataSourceHolder wrDataSourceHolder;

	@Pointcut("execution(* com..*.manager..*.*(..))")
	public void manager() {
	}

	@Before(value = "manager()")
	public void before(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		if (signature instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) signature;
			if (methodSignature.getMethod().isAnnotationPresent(Slave.class)) {
				wrDataSourceHolder.setSlave();
				return;
			}
		}
		wrDataSourceHolder.setMaster();
	}
}