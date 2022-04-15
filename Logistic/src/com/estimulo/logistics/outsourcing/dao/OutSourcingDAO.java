package com.estimulo.logistics.outsourcing.dao;

import java.util.ArrayList;

import com.estimulo.logistics.outsourcing.to.OutSourcingTO;

public interface OutSourcingDAO {

	ArrayList<OutSourcingTO> selectOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus);

}
