package com.estimulo.system.common.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

import com.estimulo.system.common.exception.*;

public class DataAccessExceptionThrowsAdvice implements ThrowsAdvice {
public final Logger logger = LoggerFactory.getLogger(DataAccessExceptionThrowsAdvice.class);
	
	public void afterThrowing(DataAccessException ex) throws Throwable {
		System.out.println("dataAccess");
		
		if(logger.isInfoEnabled()) {
			logger.info("DataAccessException afterThrowing 시작 : " + ex.getClass().getName() + " 예외 잡힘");
		}
		
		logger.error(ex.getMessage());
		
		if(logger.isInfoEnabled()) {
			logger.info("DataAccessException afterThrowing 종료");
		}
		
		throw ex;
	}
	
	public void afterThrowing(Exception ex) throws Throwable {
		
		if(logger.isInfoEnabled()) {
			logger.info("Exception afterThrowing 시작 : " + ex.getClass().getName() + " 예외 잡힘");
		}
		
		logger.error(ex.getMessage());
		
		if(logger.isInfoEnabled()) {
			logger.info("Exception afterThrowing 종료");
		}
		
		throw ex;
	}
}
