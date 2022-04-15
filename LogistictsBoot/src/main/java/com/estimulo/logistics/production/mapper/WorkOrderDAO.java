package com.estimulo.logistics.production.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.ModelMap;

import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.estimulo.logistics.production.to.WorkSiteLogTO;

@Mapper
public interface WorkOrderDAO {

	public HashMap<String,Object> getWorkOrderableMrpList(HashMap<String, String> map);
	
	public ModelMap getWorkOrderSimulationList(HashMap<String, Object> map);	
	
	public ModelMap workOrder(HashMap<String, String> map);
	
	public ArrayList<WorkOrderInfoTO> selectWorkOrderInfoList();
	
	public ModelMap workOrderCompletion(HashMap<String, String> map);
	
	public ArrayList<ProductionPerformanceInfoTO> selectProductionPerformanceInfoList();
	
	public HashMap<String,Object> selectWorkSiteSituation(HashMap<String, String> map);
	
	public void updateWorkCompletionStatus(HashMap<String, String> map);
	
	public ArrayList<WorkSiteLogTO> workSiteLogList(String workSiteLogDate);
	
}
