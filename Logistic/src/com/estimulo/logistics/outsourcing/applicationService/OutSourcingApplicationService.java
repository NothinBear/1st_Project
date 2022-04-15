package com.estimulo.logistics.outsourcing.applicationService;

import java.util.ArrayList;

import com.estimulo.logistics.outsourcing.to.OutSourcingTO;

public interface OutSourcingApplicationService {

	ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus);

}
