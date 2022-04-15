package com.estimulo.logistics.sales.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.estimulo.logistics.sales.to.SalesPlanTO;

@Mapper
public interface SalesPlanDAO {
	public ArrayList<SalesPlanTO> selectSalesPlanList(HashMap<String,String> param);
			
	public int selectSalesPlanCount(String salesPlanDate);
	
	public ArrayList<SalesPlanInMpsAvailableTO>
		selectSalesPlanListInMpsAvailable(HashMap<String,String> param);
	
	public void insertSalesPlan(SalesPlanTO TO);

	public void updateSalesPlan(SalesPlanTO TO);
	
	public void changeMpsStatusOfSalesPlan(HashMap<String,String> param);	
	
	public void deleteSalesPlan(SalesPlanTO TO);
	
}
