package com.estimulo.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.logistics.sales.serviceFacade.SalesServiceFacade;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.EstimateTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/sales/*")
public class ContractController  {
	// serviceFacade 李몄“蹂��닔 �꽑�뼵
	@Autowired
	private SalesServiceFacade salesServiceFacade;
	private ModelMap modelMap = new ModelMap();
	// GSON 占쎌뵬占쎌뵠�뇡�슢�쑎�뵳占�
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	
	@RequestMapping(value= "/searchContract.do", method=RequestMethod.POST)
	public ModelMap searchContract(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String customerCode = request.getParameter("customerCode");

		try {

			ArrayList<ContractInfoTO> contractInfoTOList = null;

				contractInfoTOList = salesServiceFacade.getContractList(searchCondition, startDate ,endDate ,customerCode);

				modelMap.put("gridRowJson", contractInfoTOList);
				modelMap.put("errorCode", 1);
				modelMap.put("errorMsg", "占쎄쉐�⑨옙");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}

	@RequestMapping(value="/searchContractDetail.do", method=RequestMethod.POST)
	public ModelMap searchContractDetail(HttpServletRequest request, HttpServletResponse response) {

		String contractNo = request.getParameter("contractNo");

		try {

			ArrayList<ContractDetailTO> contractDetailTOList = salesServiceFacade.getContractDetailList(contractNo);

			modelMap.put("gridRowJson", contractDetailTOList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "占쎄쉐�⑨옙");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		
		return modelMap;
	}
	@RequestMapping(value= "/searchEstimateInContractAvailable.do", method=RequestMethod.POST)
	public ModelMap searchEstimateInContractAvailable(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {

			ArrayList<EstimateTO> estimateListInContractAvailable = salesServiceFacade
					.getEstimateListInContractAvailable(startDate, endDate);

			modelMap.put("gridRowJson", estimateListInContractAvailable);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		
		return modelMap;
	}
	//민재
	@RequestMapping(value="/addNewContract.do", method=RequestMethod.POST)
	public ModelMap addNewContract(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList"); 
		
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			HashMap<String,String[]>workingContractList = gson.fromJson(batchList,new TypeToken<HashMap<String,String[]>>() {}.getType()) ;

			resultMap = salesServiceFacade.addNewContract(workingContractList);
			//Arraylist<contractTO> 
			modelMap.put("gridRowJson",resultMap);
		} catch (Exception e1) {
			
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());
			
		} 
		
		return modelMap;
		
	}
	@RequestMapping(value= "/cancleEstimate.do" , method=RequestMethod.POST)
	public ModelMap cancleEstimate(HttpServletRequest request, HttpServletResponse response) {

		String estimateNo = request.getParameter("estimateNo");

		try {

			salesServiceFacade.changeContractStatusInEstimate(estimateNo, "N");

			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "占쎄쉐�⑨옙");
			modelMap.put("cancledEstimateNo", estimateNo);

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		
		return modelMap;
	}

}
