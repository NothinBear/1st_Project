package com.estimulo.logistics.outsourcing.serviceFacade;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.logistics.outsourcing.applicationService.OutSourcingApplicationService;
import com.estimulo.logistics.outsourcing.to.OutSourcingTO;

@Service
public class OutSourcingServiceFacadeImpl implements OutSourcingServiceFacade{
		@Autowired
		private OutSourcingApplicationService outSourcingApplicationService;

		@Override
		public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {

			ArrayList<OutSourcingTO> OutSourcingList = null;
			
			OutSourcingList = outSourcingApplicationService.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);

			return OutSourcingList;
		}
}
