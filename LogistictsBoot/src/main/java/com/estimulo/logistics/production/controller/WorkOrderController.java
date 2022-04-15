package com.estimulo.logistics.production.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;
import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
@RestController
@RequestMapping("/production/*")
public class WorkOrderController  {
	@Autowired
	private ProductionServiceFacade productionServiceFacade;
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // serializeNulls() 속성값이 null 인 속성도 json 변환
	private ModelMap modelMap = new ModelMap();
	
	@RequestMapping(value="/getWorkOrderableMrpList.do" , method=RequestMethod.POST)
	public ModelMap getWorkOrderableMrpList(HttpServletRequest request, HttpServletResponse response) {

		try {

			modelMap = productionServiceFacade.getWorkOrderableMrpList();

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/showWorkOrderDialog.do", method=RequestMethod.POST)
	public ModelMap showWorkOrderDialog(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringNoList = request.getParameter("mrpGatheringNoList");
		String mrpNoList = request.getParameter("mrpNoList");
		
		try {
			
			modelMap = productionServiceFacade.getWorkOrderSimulationList(mrpGatheringNoList,mrpNoList);

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		
		return modelMap;
	}
	
	@RequestMapping(value="/workOrder.do", method=RequestMethod.POST)
	public ModelMap workOrder(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringNo = request.getParameter("mrpGatheringNo"); // mrpGatheringNo
		String workPlaceCode = request.getParameter("workPlaceCode"); //사업장코드
		String productionProcess = request.getParameter("productionProcessCode"); //생산공정코드:PP002
		String mrpNo = request.getParameter("mrpNo");

		try {
											//	  BRC-01		PP002
			modelMap = productionServiceFacade.workOrder(mrpGatheringNo,workPlaceCode,productionProcess,mrpNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/showWorkOrderInfoList.do", method=RequestMethod.POST)
	public ModelMap showWorkOrderInfoList(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;

		try {

			workOrderInfoList = productionServiceFacade.getWorkOrderInfoList();

			modelMap.put("gridRowJson", workOrderInfoList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		
		return modelMap;
	}
	
	@RequestMapping(value="/workOrderCompletion.do", method=RequestMethod.POST)
	public ModelMap workOrderCompletion(HttpServletRequest request, HttpServletResponse response) {

		String workOrderNo=request.getParameter("workOrderNo");
		String actualCompletionAmount=request.getParameter("actualCompletionAmount");

		try {

			modelMap = productionServiceFacade.workOrderCompletion(workOrderNo,actualCompletionAmount);

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/getProductionPerformanceInfoList.do", method=RequestMethod.POST)
	public ModelMap getProductionPerformanceInfoList(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;

		try {

			productionPerformanceInfoList = productionServiceFacade.getProductionPerformanceInfoList();

			modelMap.put("gridRowJson", productionPerformanceInfoList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/showWorkSiteSituation.do", method=RequestMethod.GET)
	public ModelMap showWorkSiteSituation(HttpServletRequest request, HttpServletResponse response) {

		String workSiteCourse = request.getParameter("workSiteCourse");//원재료검사:RawMaterials,제품제작:Production,판매제품검사:SiteExamine
		String workOrderNo = request.getParameter("workOrderNo");//작업지시일련번호	
		String itemClassIfication = request.getParameter("itemClassIfication");//품목분류:완제품,반제품,재공품	

		try {

			modelMap = productionServiceFacade.showWorkSiteSituation(workSiteCourse,workOrderNo,itemClassIfication);

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/workCompletion.do", method=RequestMethod.POST)
	public ModelMap workCompletion(HttpServletRequest request, HttpServletResponse response) {

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
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	@RequestMapping(value="/workSiteLog.do", method=RequestMethod.POST)
	public ModelMap workSiteLogList(HttpServletRequest request, HttpServletResponse response) {

		String workSiteLogDate = request.getParameter("workSiteLogDate");
		
		try {
			modelMap=productionServiceFacade.workSiteLogList(workSiteLogDate);
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	
}
