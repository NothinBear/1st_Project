package com.estimulo.logistics.outsourcing.serviceFacade;

import java.util.ArrayList;

import com.estimulo.logistics.outsourcing.applicationService.OutSourcingApplicationService;
import com.estimulo.logistics.outsourcing.to.OutSourcingTO;

public class OutSourcingServiceFacadeImpl implements OutSourcingServiceFacade{

		private OutSourcingApplicationService outSourcingApplicationService;
		public void setOutSourcingApplicationService(OutSourcingApplicationService outSourcingApplicationService) {
			this.outSourcingApplicationService=outSourcingApplicationService;
		}

		@Override
		public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {

			ArrayList<OutSourcingTO> OutSourcingList = null;
			
			OutSourcingList = outSourcingApplicationService.searchOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);

			return OutSourcingList;
		}
}
