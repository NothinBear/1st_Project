package com.estimulo.logistics.outsourcing.serviceFacade;

import java.util.ArrayList;

import com.estimulo.logistics.outsourcing.to.OutSourcingTO;

public interface OutSourcingServiceFacade {
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate,String toDate,String customerCode,String itemCode,String materialStatus);
}
