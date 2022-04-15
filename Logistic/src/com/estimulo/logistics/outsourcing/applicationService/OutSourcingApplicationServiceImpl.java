package com.estimulo.logistics.outsourcing.applicationService;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.outsourcing.dao.OutSourcingDAO;
import com.estimulo.logistics.outsourcing.dao.OutSourcingDAOImpl;
import com.estimulo.logistics.outsourcing.to.OutSourcingTO;
import com.estimulo.system.common.exception.DataAccessException;


public class OutSourcingApplicationServiceImpl implements OutSourcingApplicationService{
	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(OutSourcingApplicationServiceImpl.class);
	// 싱글톤
	private static OutSourcingApplicationService instance;

	private OutSourcingApplicationServiceImpl() {
	}

	public static OutSourcingApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ BomApplicationService 객체접근");
		}
		if(instance==null) {
			instance = new OutSourcingApplicationServiceImpl();
		}
		return instance;
	}
	//dao참조변수 선언
	private static OutSourcingDAO OutSourcingDAO = OutSourcingDAOImpl.getInstance();
	@Override
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
		// TODO Auto-generated method stub

		if (logger.isDebugEnabled()) {
			logger.debug("OutSourcingApplicationServiceImpl : searchOutSourcingList 시작");
		}

		ArrayList<OutSourcingTO> OutSourcingList = null;

		try {

			OutSourcingList = OutSourcingDAO.selectOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("OutSourcingApplicationServiceImpl : searchOutSourcingList 종료");
		}
		return OutSourcingList;
	}
}
