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

import com.estimulo.logistics.production.applicationService.MrpApplicationServiceImpl;
import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;
import com.estimulo.logistics.production.to.MrpGatheringTO;
import com.estimulo.logistics.production.to.MrpTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/production/*")
public class MrpController  {
	
	@Autowired
	private ProductionServiceFacade productionServiceFacade;
	
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚
	private ModelMap modelMap = new ModelMap();
	@RequestMapping(value="/getMrpList.do", method=RequestMethod.POST)
	public ModelMap getMrpList(HttpServletRequest request, HttpServletResponse response) {
		
		String mrpGatheringStatusCondition = request.getParameter("mrpGatheringStatusCondition");	
		String dateSearchCondition = request.getParameter("dateSearchCondition");
		String mrpStartDate = request.getParameter("mrpStartDate");
		String mrpEndDate = request.getParameter("mrpEndDate");
		String mrpGatheringNo = request.getParameter("mrpGatheringNo");
		
		try {

			ArrayList<MrpTO> mrpList = null;
			
		
			if(mrpGatheringStatusCondition != null ) {
				
				mrpList = productionServiceFacade.searchMrpList(mrpGatheringStatusCondition);
				
			} else if (dateSearchCondition != null) {
				
				mrpList = productionServiceFacade.searchMrpListAsDate(dateSearchCondition, mrpStartDate, mrpEndDate);
				
			} else if (mrpGatheringNo != null) {
				
				mrpList = productionServiceFacade.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
				
			}
			
			modelMap.put("gridRowJson", mrpList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping(value="/openMrp.do", method=RequestMethod.POST)
	public ModelMap openMrp(HttpServletRequest request, HttpServletResponse response) {
		
		String mpsNoListStr = request.getParameter("mpsNoList"); 
		
		ArrayList<String> mpsNoArr = gson.fromJson(mpsNoListStr,new TypeToken<ArrayList<String>>() { }.getType());	
		
		try {
			
			modelMap = productionServiceFacade.openMrp(mpsNoArr);
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		
		return modelMap;
	}

	@RequestMapping(value="/registerMrp.do", method=RequestMethod.POST)
	public ModelMap registerMrp(HttpServletRequest request, HttpServletResponse response) {
		
		String batchList = request.getParameter("batchList");  
		String mrpRegisterDate = request.getParameter("mrpRegisterDate"); 

		ArrayList<String> mpsList	= gson.fromJson(batchList, 
				new TypeToken<ArrayList<String>>() { }.getType());
		
		try {
			HashMap<String, Object> resultMap = productionServiceFacade.registerMrp(mrpRegisterDate, mpsList);	 
			
			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	
	
	@RequestMapping(value="/getMrpGatheringList.do", method=RequestMethod.POST)
	public ModelMap getMrpGatheringList(HttpServletRequest request, HttpServletResponse response) {
		
		String mrpNoList = request.getParameter("mrpNoList");
		
		ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
				new TypeToken<ArrayList<String>>() { }.getType());				
			//�젣�꼫由� �겢�옒�뒪瑜� �궗�슜�븷寃쎌슦 �젙�빐吏�吏� �븡�� �젣�꼫由����엯�쓣  紐낆떆�븯湲곗쐞�빐�꽌 TypeToken�쓣 �궗�슜
		try {

			ArrayList<MrpGatheringTO> mrpGatheringList = productionServiceFacade.getMrpGathering(mrpNoArr);
			
			modelMap.put("gridRowJson", mrpGatheringList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	
	
	@RequestMapping(value="/registerMrpGathering.do", method=RequestMethod.POST)
	public ModelMap registerMrpGathering(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringRegisterDate = request.getParameter("mrpGatheringRegisterDate"); //�꽑�깮�븳�궇吏�
		String mrpNoList = request.getParameter("mrpNoList");
		String mrpNoAndItemCodeList = request.getParameter("mrpNoAndItemCodeList");
		ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
				new TypeToken<ArrayList<String>>() { }.getType());
		HashMap<String, String> mrpNoAndItemCodeMap =  gson.fromJson(mrpNoAndItemCodeList, //mprNO : ItemCode 
              new TypeToken<HashMap<String, String>>() { }.getType());

		try {

			HashMap<String, Object> resultMap  = productionServiceFacade.registerMrpGathering(mrpGatheringRegisterDate, mrpNoArr,mrpNoAndItemCodeMap);	 
//														�꽑�깮�븳�궇吏�                  				getRowData		MRP-NO : DK-AP01
			
			
			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	
	@RequestMapping(value="/searchMrpGathering.do", method=RequestMethod.POST)
	public ModelMap searchMrpGathering(HttpServletRequest request, HttpServletResponse response) {

		String searchDateCondition = request.getParameter("searchDateCondition");
		String startDate = request.getParameter("mrpGatheringStartDate");
		String endDate = request.getParameter("mrpGatheringEndDate");
		
		try {

			ArrayList<MrpGatheringTO> mrpGatheringList = 
					productionServiceFacade.searchMrpGatheringList(searchDateCondition, startDate, endDate);
			
			modelMap.put("gridRowJson", mrpGatheringList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		return modelMap;
	}
	
	
}
