package com.estimulo.logistics.production.applicationService;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.estimulo.logistics.production.mapper.WorkOrderDAO;
import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.estimulo.logistics.production.to.WorkOrderSimulationTO;
import com.estimulo.logistics.production.to.WorkSiteLogTO;


@Component
public class WorkOrderApplicationServiceImpl implements WorkOrderApplicationService {

	@Autowired
	private WorkOrderDAO workOrderDAO;

	@Override
	public ModelMap getWorkOrderableMrpList() {
		ModelMap resultMap = new ModelMap();
		HashMap<String, String> map = new HashMap<>();
		workOrderDAO.getWorkOrderableMrpList(map);
		resultMap.put("gridRowJson", map.get("RESULT"));
        resultMap.put("errorCode",map.get("ERROR_CODE"));
        resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;
	}

	@Override
	public ModelMap getWorkOrderSimulationList(String mrpGatheringNoList, String mrpNoList) {

		mrpGatheringNoList = mrpGatheringNoList.replace("[", "").replace("]", "").replace("{", "").replace("}", "")
				.replace("\"", "");
		mrpNoList = mrpNoList.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");

		HashMap<String, Object> map = new HashMap<>();
		map.put("mrpGatheringNoList", mrpGatheringNoList);		
		map.put("mrpNoList", mrpNoList);
		workOrderDAO.getWorkOrderSimulationList(map);
		ModelMap modelMap = new ModelMap();
		
		@SuppressWarnings("unchecked")
		List<WorkOrderSimulationTO> list = (List<WorkOrderSimulationTO>)map.get("RESULT");
		for(WorkOrderSimulationTO been:list) {
			if(Integer.parseInt(been.getStockAfterWork())<0) {
				been.setStockAfterWork("재고부족");
			}
		};
		
		modelMap.put("gridRowJson", map.get("RESULT"));
		modelMap.put("errorCode",map.get("ERROR_CODE"));
		modelMap.put("errorMsg", map.get("ERROR_MSG"));
		return modelMap;

	}

	@Override
	public ModelMap workOrder(String mrpGatheringNo, String workPlaceCode, String productionProcess,
			String mrpNo) {

		mrpGatheringNo = mrpGatheringNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "")
				.replace("\"", "");
		mrpNo = mrpNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
		
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("workPlaceCode", workPlaceCode);
		map.put("productionProcess", productionProcess);
		map.put("mrpNo", mrpNo);	
		
		workOrderDAO.workOrder(map);
		
		ModelMap resultMap = new ModelMap();

		resultMap.put("errorCode",map.get("ERROR_CODE"));
	    resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;

	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {

		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;

		workOrderInfoList = workOrderDAO.selectWorkOrderInfoList();

		return workOrderInfoList;

	}

	@Override
	public ModelMap workOrderCompletion(String workOrderNo, String actualCompletionAmount) {

		HashMap<String, String> map=new HashMap<>();
		map.put("workOrderNo", workOrderNo);
		map.put("actualCompletionAmount", actualCompletionAmount);
		workOrderDAO.workOrderCompletion(map);
		ModelMap resultMap = new ModelMap();
		resultMap.put("errorCode",map.get("ERROR_CODE"));
	    resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;

	}

	@Override
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {

		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;

		productionPerformanceInfoList = workOrderDAO.selectProductionPerformanceInfoList();

		return productionPerformanceInfoList;

	}

	@Override
	public ModelMap showWorkSiteSituation(String workSiteCourse, String workOrderNo,
			String itemClassIfication) {
		
		HashMap<String,String> map = new HashMap<>();
	  	map.put("workOrderNo", workOrderNo);
	  	map.put("workSiteCourse", workSiteCourse);
	  	map.put("itemClassIfication", itemClassIfication);
	  	
		workOrderDAO.selectWorkSiteSituation(map);
		ModelMap resultMap = new ModelMap();
	  	resultMap.put("gridRowJson",map.get("RESULT"));
	  	resultMap.put("errorCode",map.get("ERROR_CODE"));
	  	resultMap.put("errorMsg",map.get("ERROR_MSG"));
	  	
		return resultMap;
	}

	@Override
	public void workCompletion(String workOrderNo, String itemCode, ArrayList<String> itemCodeListArr) {

		String itemCodeList = itemCodeListArr.toString().replace("[", "").replace("]", "");
		HashMap<String,String> map = new HashMap<>();
	  	map.put("workOrderNo", workOrderNo);
	  	map.put("itemCode", itemCode);
	  	map.put("itemCodeList", itemCodeList);
	  	
		workOrderDAO.updateWorkCompletionStatus(map);

	}

	@Override
	public ModelMap workSiteLogList(String workSiteLogDate) {

		ArrayList<WorkSiteLogTO> list = workOrderDAO.workSiteLogList(workSiteLogDate);
		ModelMap resultMap = new ModelMap();
   	  	
		resultMap.put("gridRowJson",list);

		return resultMap;
	}

}
