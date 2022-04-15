package com.estimulo.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;
import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WorkOrderController extends MultiActionController {

	private ProductionServiceFacade productionServiceFacade;
	public void setProductionServiceFacade(ProductionServiceFacade productionServiceFacade) {
		this.productionServiceFacade=productionServiceFacade;
	}

	private static Gson gson = new GsonBuilder().serializeNulls().create(); // serializeNulls() 속성값이 null 인 속성도 json 변환

	public ModelAndView getWorkOrderableMrpList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			resultMap = productionServiceFacade.getWorkOrderableMrpList();

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",resultMap);
	}

	public ModelAndView showWorkOrderDialog(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringNoList = request.getParameter("mrpGatheringNoList");
		String mrpNoList = request.getParameter("mrpNoList");
		
		HashMap<String,Object> resultMap = new HashMap<>();

		try {
			
			resultMap = productionServiceFacade.getWorkOrderSimulationList(mrpGatheringNoList,mrpNoList);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		
		return new ModelAndView("jsonView",resultMap);
	}

	public ModelAndView workOrder(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringNo = request.getParameter("mrpGatheringNo"); // mrpGatheringNo
		String workPlaceCode = request.getParameter("workPlaceCode"); //사업장코드
		String productionProcess = request.getParameter("productionProcessCode"); //생산공정코드:PP002
		String mrpNo = request.getParameter("mrpNo");
		HashMap<String,Object> resultMap = new HashMap<>();

		try {
											//	  BRC-01		PP002
			resultMap = productionServiceFacade.workOrder(mrpGatheringNo,workPlaceCode,productionProcess,mrpNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",resultMap);
	}

	public ModelAndView showWorkOrderInfoList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();

		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;

		try {

			workOrderInfoList = productionServiceFacade.getWorkOrderInfoList();

			map.put("gridRowJson", workOrderInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView workOrderCompletion(HttpServletRequest request, HttpServletResponse response) {


		String workOrderNo=request.getParameter("workOrderNo");
		String actualCompletionAmount=request.getParameter("actualCompletionAmount");
		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			resultMap = productionServiceFacade.workOrderCompletion(workOrderNo,actualCompletionAmount);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",resultMap);
	}
	
	public ModelAndView getProductionPerformanceInfoList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;

		try {

			productionPerformanceInfoList = productionServiceFacade.getProductionPerformanceInfoList();

			map.put("gridRowJson", productionPerformanceInfoList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView showWorkSiteSituation(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> resultMap = new HashMap<>();
		
		String workSiteCourse = request.getParameter("workSiteCourse");//원재료검사:RawMaterials,제품제작:Production,판매제품검사:SiteExamine
		String workOrderNo = request.getParameter("workOrderNo");//작업지시일련번호	
		String itemClassIfication = request.getParameter("itemClassIfication");//품목분류:완제품,반제품,재공품	

		try {

			resultMap = productionServiceFacade.showWorkSiteSituation(workSiteCourse,workOrderNo,itemClassIfication);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",resultMap);
	}
	
	public ModelAndView workCompletion(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> resultMap = new HashMap<>();
		
		String workOrderNo = request.getParameter("workOrderNo"); //작업지시번호
		String itemCode = request.getParameter("itemCode"); //제작품목코드 DK-01 , DK-AP01
		String itemCodeList = request.getParameter("itemCodeList"); //작업품목코드 
		ArrayList<String> itemCodeListArr = gson.fromJson(itemCodeList,
				new TypeToken<ArrayList<String>>() {}.getType());
		//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
		try {
			productionServiceFacade.workCompletion(workOrderNo,itemCode,itemCodeListArr);

		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",resultMap);
	}
	
	public ModelAndView workSiteLogList(HttpServletRequest request, HttpServletResponse response) {

		String workSiteLogDate = request.getParameter("workSiteLogDate");
		
		HashMap<String, Object> resultMap = new HashMap<>();

		try {
			resultMap=productionServiceFacade.workSiteLogList(workSiteLogDate);
		} catch (Exception e1) {
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",resultMap);
	}
	
}
