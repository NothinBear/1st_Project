package com.estimulo.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;
import com.estimulo.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.estimulo.logistics.production.to.MpsTO;
import com.estimulo.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MpsController extends MultiActionController {

	private ProductionServiceFacade productionServiceFacade;
	public void setProductionServiceFacade(ProductionServiceFacade productionServiceFacade) {
		this.productionServiceFacade=productionServiceFacade;
	}
	// gson �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚

	public ModelAndView searchMpsInfo(HttpServletRequest request, HttpServletResponse response) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String includeMrpApply = request.getParameter("includeMrpApply"); 

		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<MpsTO> mpsTOList = productionServiceFacade.getMpsList(startDate, endDate, includeMrpApply);

			map.put("gridRowJson", mpsTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView searchContractDetailListInMpsAvailable(HttpServletRequest request,
			HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition"); //�닔二쇱씪�옄 = contractDate, �궔湲곗씪 = dueDateOfContract
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = productionServiceFacade
					.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
													   //contractDate, 2019-07-01, 2019-07-31
			map.put("gridRowJson", contractDetailInMpsAvailableList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView searchSalesPlanListInMpsAvailable(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		HashMap<String, Object> map = new HashMap<>();
		try {

			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = productionServiceFacade
					.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

			map.put("gridRowJson", salesPlanInMpsAvailableList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView convertContractDetailToMps(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList"); // �닔二쇱긽�꽭 �뜲�씠�꽣 
				
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<ContractDetailInMpsAvailableTO>>() {
				}.getType());
				//�젣�꼫由��겢�옒�뒪瑜� �궗�슜�븷寃쎌슦 而댄뙆�씪�떆 �옄�룞�쑝濡� ContractDetailInMpsAvailableTO �씪怨� �븳 �씠由꾩씠 Object 濡� 諛붾�뚯뼱�꽌 �뿭吏곷젹�솕媛� �뼱�젮�썙吏꾨떎 洹몃옒�꽌 �씠�젃寃� �븳�떎怨좏븳�떎
		HashMap<String, Object> map = new HashMap<>();

		try {

			HashMap<String, Object> resultMap = productionServiceFacade
					.convertContractDetailToMps(contractDetailInMpsAvailableList);

			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView convertSalesPlanToMps(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<SalesPlanInMpsAvailableTO>>() {
				}.getType());

		HashMap<String, Object> map = new HashMap<>();

		try {

			HashMap<String, Object> resultMap = productionServiceFacade.convertSalesPlanToMps(salesPlanInMpsAvailableList);

			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}

}
