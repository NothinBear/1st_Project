package com.estimulo.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.estimulo.logistics.production.to.WorkOrderSimulationTO;
import com.estimulo.logistics.production.to.WorkOrderableMrpListTO;
import com.estimulo.logistics.production.to.WorkSiteLog;
import com.estimulo.logistics.production.to.WorkSiteSimulationTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class WorkOrderDAOImpl extends IBatisSupportDAO implements WorkOrderDAO {
	// HERE
	@Override
	public HashMap<String, Object> getWorkOrderableMrpList() {
		HashMap<String, String> map = new HashMap<>();

		ArrayList<WorkOrderableMrpListTO> workOrderableMrpList = (ArrayList<WorkOrderableMrpListTO>) getSqlMapClientTemplate()
				.queryForList("workOrder.getWorkOrderableMrpList", map);

		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("gridRowJson", workOrderableMrpList);
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

		return resultMap;
	}

	// HERE
	@Override
	public HashMap<String, Object> getWorkOrderSimulationList(String mrpGatheringNo, String mrpNo) {
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("mrpNo", mrpNo);
		ArrayList<WorkOrderSimulationTO> workOrderSimulationList = (ArrayList<WorkOrderSimulationTO>) getSqlMapClientTemplate()
				.queryForList("workOrder.getWorkOrderSimulationList", map);

		HashMap<String, Object> resultMap = new HashMap<>();

		resultMap.put("gridRowJson", workOrderSimulationList);
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

		return resultMap;
	}

	@Override
	public HashMap<String, Object> workOrder(String mrpGatheringNo, String workPlaceCode, String productionProcess,
			String mrpNo) {
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("workPlaceCode", workPlaceCode);
		map.put("productionProcess", productionProcess);
		map.put("mrpNo", mrpNo);

		getSqlMapClientTemplate().update("workOrder.workOrder", map);

		HashMap<String, Object> resultMap = new HashMap<>();

		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));
		return resultMap;
	}

	@Override
	public ArrayList<WorkOrderInfoTO> selectWorkOrderInfoList() {
		return (ArrayList<WorkOrderInfoTO>) getSqlMapClientTemplate().queryForList("workOrder.selectWorkOrderInfoList");
	}

	@Override
	public HashMap<String, Object> workOrderCompletion(String workOrderNo, String actualCompletionAmount) {
		HashMap<String, String> map = new HashMap<>();
		map.put("workOrderNo", workOrderNo);
		map.put("actualCompletionAmount", actualCompletionAmount);

		getSqlMapClientTemplate().update("workOrder.workOrderCompletion", map);

		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

		return resultMap;
	}

	@Override
	public ArrayList<ProductionPerformanceInfoTO> selectProductionPerformanceInfoList() {
		return (ArrayList<ProductionPerformanceInfoTO>) getSqlMapClientTemplate()
				.queryForList("workOrder.selectProductionPerformanceInfoList");
	}

	@Override
	public HashMap<String, Object> selectWorkSiteSituation(String workSiteCourse, String workOrderNo,
			String itemClassIfication) {
		HashMap<String, String> map = new HashMap<>();
		map.put("workOrderNo", workOrderNo);
		map.put("workSiteCourse", workSiteCourse);
		map.put("itemClassIfication", itemClassIfication);

		ArrayList<WorkSiteSimulationTO> list = (ArrayList<WorkSiteSimulationTO>) getSqlMapClientTemplate()
				.queryForList("workOrder.selectWorkSiteSituation", map);
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("gridRowJson", list);
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

		return resultMap;
	}

	@Override
	public void updateWorkCompletionStatus(String workOrderNo, String itemCode, String itemCodeList) {
		HashMap<String, String> map = new HashMap<>();
		map.put("workOrderNo", workOrderNo);
		map.put("itemCode", itemCode);
		map.put("itemCodeList", itemCodeList);

		getSqlMapClientTemplate().update("workOrder.updateWorkCompletionStatus", map);
	}

	@Override
	public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {
		ArrayList<WorkSiteLog> list = (ArrayList<WorkSiteLog>) getSqlMapClientTemplate()
				.queryForList("workOrder.workSiteLogList", workSiteLogDate);
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("gridRowJson", list);

		return resultMap;
	}

}