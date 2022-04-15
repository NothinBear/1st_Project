package com.estimulo.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;
import com.estimulo.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.estimulo.logistics.production.to.MpsTO;
import com.estimulo.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/production/*")
public class MpsController  {
	
	@Autowired
	private ProductionServiceFacade productionServiceFacade;
	private ModelMap modelmap = new ModelMap();

	private static Gson gson = new GsonBuilder().serializeNulls().create(); 
	
	@RequestMapping(value="/searchMpsInfo.do", method=RequestMethod.POST)
	public ModelMap searchMpsInfo(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String includeMrpApply = request.getParameter("includeMrpApply"); 

		try {

			ArrayList<MpsTO> mpsTOList = productionServiceFacade.getMpsList(startDate, endDate, includeMrpApply);

			modelmap.put("gridRowJson", mpsTOList);
			modelmap.put("errorCode", 1);
			modelmap.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			modelmap.put("errorCode", -1);
			modelmap.put("errorMsg", e1.getMessage());

		} 
		return modelmap;
	}
	@RequestMapping(value = "/searchContractDetailInMpsAvailable.do", method = RequestMethod.POST)
	public ModelMap searchContractDetailListInMpsAvailable(HttpServletRequest request,
			HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition"); 
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {

			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = productionServiceFacade
					.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
													   //contractDate, 2019-07-01, 2019-07-31
			modelmap.put("gridRowJson", contractDetailInMpsAvailableList);
			modelmap.put("errorCode", 1);
			modelmap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelmap.put("errorCode", -1);
			modelmap.put("errorMsg", e1.getMessage());

		}
		return modelmap;
	}
	 @RequestMapping(value="/searchSalesPlanListInMpsAvailable.do" , method=RequestMethod.POST)
	public ModelMap searchSalesPlanListInMpsAvailable(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {

			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = productionServiceFacade
					.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

			modelmap.put("gridRowJson", salesPlanInMpsAvailableList);
			modelmap.put("errorCode", 1);
			modelmap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelmap.put("errorCode", -1);
			modelmap.put("errorMsg", e1.getMessage());

		}
		return modelmap;
	}
	@RequestMapping(value = "/convertContractDetailToMps.do", method = RequestMethod.POST)
	public ModelMap convertContractDetailToMps(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");  
				
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<ContractDetailInMpsAvailableTO>>() {
				}.getType());
		
		try {

			HashMap<String, Object> resultMap = productionServiceFacade
					.convertContractDetailToMps(contractDetailInMpsAvailableList);

			modelmap.put("result", resultMap);
			modelmap.put("errorCode", 1);
			modelmap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelmap.put("errorCode", -1);
			modelmap.put("errorMsg", e1.getMessage());

		} 
		return modelmap;
	}
	@RequestMapping(value = "/convertSalesPlanToMps.do", method = RequestMethod.POST)
	public ModelMap convertSalesPlanToMps(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<SalesPlanInMpsAvailableTO>>() {
				}.getType());

		try {

			HashMap<String, Object> resultMap = productionServiceFacade.convertSalesPlanToMps(salesPlanInMpsAvailableList);

			modelmap.put("result", resultMap);
			modelmap.put("errorCode", 1);
			modelmap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelmap.put("errorCode", -1);
			modelmap.put("errorMsg", e1.getMessage());

		} 
		return modelmap;
	}

}
