package com.estimulo.system.common.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAdvice implements MethodInterceptor {
private final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		if(logger.isInfoEnabled()) {
			logger.info(invocation.getThis().getClass().getSimpleName() + " : " + invocation.getMethod().getName() + "시작");
			
			Object[] args = invocation.getArguments();
			if((args != null) && (args.length > 0)) {
				for(int i = 0; i < args.length; i++) {
					logger.info("매개변수 [ " + i  + " ] : " + args[i]);
				}
			}
		}
		
		Object returnValue = invocation.proceed();
		
		if(logger.isInfoEnabled()) {
			logger.info(invocation.getThis().getClass().getSimpleName() + " : " + invocation.getMethod().getName() + "종료");
		}
		
		return returnValue;
	}
}
