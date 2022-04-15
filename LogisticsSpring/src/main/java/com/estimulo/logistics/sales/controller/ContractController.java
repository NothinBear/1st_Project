package com.estimulo.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.sales.serviceFacade.SalesServiceFacade;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.EstimateTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ContractController extends MultiActionController {
	// serviceFacade 李몄“蹂��닔 �꽑�뼵
	private SalesServiceFacade salesServiceFacade;
	public void setSalesServiceFacade(SalesServiceFacade salesServiceFacade) {
		this.salesServiceFacade = salesServiceFacade;
	}
	// GSON 占쎌뵬占쎌뵠�뇡�슢�쑎�뵳占�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 占쎈꺗占쎄쉐揶쏅�れ뵠 null 占쎌뵥 占쎈꺗占쎄쉐占쎈즲 癰귨옙占쎌넎

	public ModelAndView searchContract(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String customerCode = request.getParameter("customerCode");

		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<ContractInfoTO> contractInfoTOList = null;

				contractInfoTOList = salesServiceFacade.getContractList(searchCondition, startDate ,endDate ,customerCode);

			map.put("gridRowJson", contractInfoTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "占쎄쉐�⑨옙");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",map);
	}

//	占쎌삂占쎈씜占쎄국
	public ModelAndView searchContractDetail(HttpServletRequest request, HttpServletResponse response) {

		String contractNo = request.getParameter("contractNo");

		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<ContractDetailTO> contractDetailTOList = salesServiceFacade.getContractDetailList(contractNo);

			map.put("gridRowJson", contractDetailTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "占쎄쉐�⑨옙");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView searchEstimateInContractAvailable(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<EstimateTO> estimateListInContractAvailable = salesServiceFacade
					.getEstimateListInContractAvailable(startDate, endDate);

			map.put("gridRowJson", estimateListInContractAvailable);
			map.put("errorCode", 1);
			map.put("errorMsg", "�꽦怨�");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView addNewContract(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");  // �닔二쇰벑濡앺븷 寃ъ쟻�뜲�씠�꽣 json 臾몄옄�뿴 
		
		HashMap<String, Object> resultMap = new HashMap<>();

		try {
			
			HashMap<String,String[]>workingContractList = gson.fromJson(batchList,new TypeToken<HashMap<String,String[]>>() {}.getType()) ;
			resultMap = salesServiceFacade.addNewContract(workingContractList);
			//Arraylist<contractTO> 

		} catch (Exception e1) {
			
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());
			
		} 
		
		return new ModelAndView("jsonView",resultMap);
		
	}

	public ModelAndView cancleEstimate(HttpServletRequest request, HttpServletResponse response) {

		String estimateNo = request.getParameter("estimateNo");

		HashMap<String, Object> map = new HashMap<>();

		try {

			salesServiceFacade.changeContractStatusInEstimate(estimateNo, "N");

			map.put("errorCode", 1);
			map.put("errorMsg", "占쎄쉐�⑨옙");
			map.put("cancledEstimateNo", estimateNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		
		return new ModelAndView("jsonView",map);
	}

}
