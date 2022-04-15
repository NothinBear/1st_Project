package com.estimulo.logistics.production.applicationService;

import java.util.ArrayList;
import org.springframework.ui.ModelMap;

import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;


public interface WorkOrderApplicationService {

	public ModelMap getWorkOrderableMrpList();

	public ModelMap getWorkOrderSimulationList(String mrpGatheringNoList,String mrpNoList);	
	
	public ModelMap workOrder(String mrpGatheringNo,String workPlaceCode,String productionProcess,String mrpNo);
	
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList();
	
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList();

	public ModelMap workOrderCompletion(String workOrderNo,String actualCompletionAmount);
	
	public ModelMap showWorkSiteSituation(String workSiteCourse,String workOrderNo, String itemClassIfication);
	
	public void workCompletion(String workOrderNo,String itemCode , ArrayList<String> itemCodeListArr);
	
	public ModelMap workSiteLogList(String workSiteLogDate);
	
} 
 