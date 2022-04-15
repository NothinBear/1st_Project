package com.estimulo.logistics.outsourcing.applicationService;

import java.util.ArrayList;

import com.estimulo.logistics.outsourcing.dao.OutSourcingDAO;
import com.estimulo.logistics.outsourcing.to.OutSourcingTO;


public class OutSourcingApplicationServiceImpl implements OutSourcingApplicationService{

	//dao참조변수 선언
	private OutSourcingDAO outSourcingDAO;
	public void setOutSourcingDAO(OutSourcingDAO outSourcingDAO) {
		this.outSourcingDAO=outSourcingDAO;
	}
	@Override
	public ArrayList<OutSourcingTO> searchOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
		// TODO Auto-generated method stub

		ArrayList<OutSourcingTO> OutSourcingList = null;

			OutSourcingList = outSourcingDAO.selectOutSourcingList(fromDate,toDate,customerCode,itemCode,materialStatus);

		return OutSourcingList;
	}
}
