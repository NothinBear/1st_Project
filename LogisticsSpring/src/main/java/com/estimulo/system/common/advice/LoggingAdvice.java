package com.estimulo.system.common.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingAdvice implements MethodInterceptor{
	private final Log logger = LogFactory.getLog(getClass());
	
	public Object invoke(MethodInvocation invocation) throws Throwable{
		String className = invocation.getThis().getClass().getName();
		if(logger.isDebugEnabled()) {
			logger.debug(className+"."+invocation.getMethod().getName()+"()"+"  시작");
			Object[] args = invocation.getArguments();
			if((args!=null)&&(args.length>0)) {
				for(int i=0;i<args.length;i++) {
					logger.debug("Argument["+i+"] : "+args[i]);
				}
			}
		}
		Object retVal = invocation.proceed();
		if(logger.isDebugEnabled()) {
			logger.debug(className+"."+invocation.getMethod().getName()+"()"+"  종료");
		}
		return retVal;
	}
}
