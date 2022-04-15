package com.estimulo.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.logistics.production.dao.WorkOrderDAO;
import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;

public class WorkOrderApplicationServiceImpl implements WorkOrderApplicationService {

		// DAO 참조변수 선언
		// private static MpsDAO mpsDAO = MpsDAOImpl.getInstance();
		// private static MrpDAO mrpDAO = MrpDAOImpl.getInstance();
		private WorkOrderDAO workOrderDAO;
		
		public void setWorkOrderDAO(WorkOrderDAO workOrderDAO) {
			this.workOrderDAO = workOrderDAO;
		}

		@Override
		public HashMap<String,Object> getWorkOrderableMrpList() {

            HashMap<String,Object> resultMap = null;

				resultMap = workOrderDAO.getWorkOrderableMrpList();
				
			return resultMap;
			
	}

		@Override
		public HashMap<String,Object> getWorkOrderSimulationList(String mrpGatheringNoList,String mrpNoList) {

			mrpGatheringNoList=mrpGatheringNoList.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
			mrpNoList=mrpNoList.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
            HashMap<String,Object> resultMap = null;

				resultMap = workOrderDAO.getWorkOrderSimulationList(mrpGatheringNoList,mrpNoList);

			return resultMap;
			
		}

		@Override
		public HashMap<String,Object> workOrder(String mrpGatheringNo, String workPlaceCode,String productionProcess,String mrpNo) {


			
			mrpGatheringNo=mrpGatheringNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
			mrpNo=mrpNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
            HashMap<String,Object> resultMap = null;

				resultMap = workOrderDAO.workOrder(mrpGatheringNo,workPlaceCode,productionProcess,mrpNo);

        	return resultMap;			
			
		}

		@Override
		public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {


			ArrayList<WorkOrderInfoTO> workOrderInfoList = null;

				workOrderInfoList = workOrderDAO.selectWorkOrderInfoList();
				
			return workOrderInfoList;
			
		}

		@Override
		public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {
			
            HashMap<String,Object> resultMap = null;
            
				resultMap = workOrderDAO.workOrderCompletion(workOrderNo,actualCompletionAmount);

        	return resultMap;
			
		}
		
		@Override
		public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {

			ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;

				productionPerformanceInfoList = workOrderDAO.selectProductionPerformanceInfoList();
				
			return productionPerformanceInfoList;
			
		}

		@Override
		public HashMap<String,Object> showWorkSiteSituation(String workSiteCourse,String workOrderNo,String itemClassIfication) {
			
			HashMap<String,Object> showWorkSiteSituation = null;

				showWorkSiteSituation = workOrderDAO.selectWorkSiteSituation(workSiteCourse,workOrderNo,itemClassIfication);

			return showWorkSiteSituation;
		}

		@Override
		public void workCompletion(String workOrderNo, String itemCode ,  ArrayList<String> itemCodeListArr) {
				
				String itemCodeList=itemCodeListArr.toString().replace("[", "").replace("]", "");
				
				workOrderDAO.updateWorkCompletionStatus(workOrderNo,itemCode,itemCodeList);
				
		}

		@Override
		public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {

			HashMap<String, Object> resultMap = new HashMap<>();

				resultMap=workOrderDAO.workSiteLogList(workSiteLogDate);
				
			return resultMap;
		}
		
}
