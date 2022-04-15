package com.estimulo.logistics.outsourcing.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.outsourcing.to.OutSourcingTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

public class OutSourcingDAOImpl extends IBatisSupportDAO implements OutSourcingDAO{
	@SuppressWarnings("unchecked")
	@Override
   public ArrayList<OutSourcingTO> selectOutSourcingList(String fromDate, String toDate, String customerCode,String itemCode,String materialStatus) {
      // TODO Auto-generated method stub
	  HashMap<String,String> params = new HashMap<>();
	  params.put("fromDate", fromDate);
	  params.put("toDate", toDate);
	  params.put("customerCode", customerCode);
	  params.put("itemCode", itemCode);
	  params.put("materialStatus", materialStatus);
        
      return (ArrayList<OutSourcingTO>) getSqlMapClientTemplate().queryForList("outsourcing.selectOutSourcingList",params);
   }
}