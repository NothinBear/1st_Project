package com.estimulo.system.common.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.dao.DataAccessException;

public class DataAccessThrowsAdvice implements ThrowsAdvice{
	private final Log logger = LogFactory.getLog(getClass());
	public void afterThrowing(DataAccessException dae) throws Throwable{
		if(logger.isDebugEnabled()) {
			logger.debug("DataAccessException Caught : "+dae.getClass().getName());
		}
		if(logger.isErrorEnabled()) {
			logger.error(dae.getMessage());
		}
	}
}
