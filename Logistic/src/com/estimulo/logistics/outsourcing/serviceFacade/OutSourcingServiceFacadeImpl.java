package com.estimulo.logistics.outsourcing.serviceFacade;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.outsourcing.applicationService.OutSourcingApplicationService;
import com.estimulo.logistics.outsourcing.applicationService.OutSourcingApplicationServiceImpl;
import com.estimulo.logistics.outsourcing.to.OutSourcingTO;
import com.estimulo.system.common.db.DataSourceTransactionManager;
import com.estimulo.system.common.exception.DataAccessException;

public class OutSourcingServiceFacadeImpl implements OutSourcingServiceFacade{
	// SLF4J logger
		private static Logger logger = LoggerFactory.getLogger(OutSourcingServiceFacadeImpl.class);
		// 싱글톤
		private static OutSourcingServiceFacade instance;

		private OutSourcingServiceFacadeImpl() {
		}

		public static OutSourcingServiceFacade getInstance() {

			if (logger.isDebugEnabled()) {
				logger.debug("@ PurchaseServiceFacadeImpl 객체접근");
			}
			if(instance == null) {
				instance=new OutSourcingServiceFacadeImpl();
			}
			return instance;
		}

		// 참조변수 선언
		private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
				.getInstance();
		private static OutSourcingApplicationService OutSourcingApplicationService=OutSourcingApplicationServiceImpl.getInstance();

		@Override
		public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
			// TODO Auto-generated method stub
			if (logger.isDebugEnabled()) {
				logger.debug("OutSourcingServiceFacadeImpl : searchOutSourcingList 시작");
			}

			dataSourceTransactionManager.beginTransaction(false);
			ArrayList<OutSourcingTO> OutSourcingList = null;

			try {

				OutSourcingList = OutSourcingApplicationService.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);
				dataSourceTransactionManager.commitTransaction();

			} catch (DataAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				dataSourceTransactionManager.rollbackTransaction();
				throw e;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("OutSourcingServiceFacadeImpl : searchOutSourcingList 시작");
			}

			return OutSourcingList;
		}
}
