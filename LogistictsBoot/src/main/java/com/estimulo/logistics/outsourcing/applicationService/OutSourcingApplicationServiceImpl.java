package com.estimulo.logistics.outsourcing.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.outsourcing.mapper.OutSourcingDAO;
import com.estimulo.logistics.outsourcing.to.OutSourcingTO;

@Component
public class OutSourcingApplicationServiceImpl implements OutSourcingApplicationService{

	@Autowired
	private OutSourcingDAO outSourcingDAO;
	@Override
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
		
		HashMap<String,String> params = new HashMap<>();
		
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		params.put("customerCode", customerCode);
		params.put("itemCode", itemCode);
		params.put("materialStatus", materialStatus);

		return outSourcingDAO.selectOutSourcingList(params);
	}
}
