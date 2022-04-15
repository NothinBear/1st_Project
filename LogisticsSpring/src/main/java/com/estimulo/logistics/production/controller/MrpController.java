package com.estimulo.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;
import com.estimulo.logistics.production.to.MrpGatheringTO;
import com.estimulo.logistics.production.to.MrpTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MrpController extends MultiActionController {
	
	private ProductionServiceFacade productionServiceFacade;
	public void setProductionServiceFacade(ProductionServiceFacade productionServiceFacade) {
		this.productionServiceFacade=productionServiceFacade;
	}
	// gson �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚
	
	
	
	public ModelAndView getMrpList(HttpServletRequest request, HttpServletResponse response) {
		
		String mrpGatheringStatusCondition = request.getParameter("mrpGatheringStatusCondition");	
		String dateSearchCondition = request.getParameter("dateSearchCondition");
		String mrpStartDate = request.getParameter("mrpStartDate");
		String mrpEndDate = request.getParameter("mrpEndDate");
		String mrpGatheringNo = request.getParameter("mrpGatheringNo");
	
		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<MrpTO> mrpList = null;
			
		
			if(mrpGatheringStatusCondition != null ) {
				//�뿬湲� null�씠�씪�뒗 �뒪�듃留곴컪�씠 �떞寃⑥��솕�쑝�땲 null�� �븘�떂. 媛앹껜媛��엳�뒗�긽�깭.
				
				mrpList = productionServiceFacade.searchMrpList(mrpGatheringStatusCondition);
				
			} else if (dateSearchCondition != null) {
				
				mrpList = productionServiceFacade.searchMrpListAsDate(dateSearchCondition, mrpStartDate, mrpEndDate);
				
			} else if (mrpGatheringNo != null) {
				
				mrpList = productionServiceFacade.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
				
			}
			
			map.put("gridRowJson", mrpList);
			map.put("errorCode", 1);
			map.put("errorMsg", "�꽦怨�");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",map);
	}
	
	
	public ModelAndView openMrp(HttpServletRequest request, HttpServletResponse response) {
		
		String mpsNoListStr = request.getParameter("mpsNoList"); 
		
		ArrayList<String> mpsNoArr = gson.fromJson(mpsNoListStr,
				new TypeToken<ArrayList<String>>() { }.getType());		
		//�젣�꼫由� �겢�옒�뒪瑜� �궗�슜�븷寃쎌슦 �젙�빐吏�吏� �븡�� �젣�꼫由����엯�쓣  紐낆떆�븯湲곗쐞�빐�꽌 TypeToken�쓣 �궗�슜
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			
			resultMap = productionServiceFacade.openMrp(mpsNoArr);

		} catch (Exception e1) {
			
			e1.printStackTrace();
			resultMap.put("errorCode", -1);
			resultMap.put("errorMsg", e1.getMessage());

		}
		
		return new ModelAndView("jsonView",resultMap);
	}

	
	public ModelAndView registerMrp(HttpServletRequest request, HttpServletResponse response) {
		
		String batchList = request.getParameter("batchList");  // mrp 紐⑥쓽�쟾媛� �젙蹂� 
		String mrpRegisterDate = request.getParameter("mrpRegisterDate");  // �냼�슂�웾 �쟾媛� �씪�옄 

		ArrayList<String> mpsList	= gson.fromJson(batchList, 
				new TypeToken<ArrayList<String>>() { }.getType()); // 媛곴컖�쓽 json�씠 to媛앹껜媛� �릺�뼱 AraryList�뿉 �떞源�
		//�젣�꼫由� �겢�옒�뒪瑜� �궗�슜�븷寃쎌슦 �젙�빐吏�吏� �븡�� �젣�꼫由����엯�쓣  紐낆떆�븯湲곗쐞�빐�꽌 TypeToken�쓣 �궗�슜
		HashMap<String, Object> map = new HashMap<>();

		try {
			HashMap<String, Object> resultMap = productionServiceFacade.registerMrp(mrpRegisterDate, mpsList);	 
			
			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "�꽦怨�");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
	
	
	
	public ModelAndView getMrpGatheringList(HttpServletRequest request, HttpServletResponse response) {
		
		String mrpNoList = request.getParameter("mrpNoList");
		
		ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
				new TypeToken<ArrayList<String>>() { }.getType());				
			//�젣�꼫由� �겢�옒�뒪瑜� �궗�슜�븷寃쎌슦 �젙�빐吏�吏� �븡�� �젣�꼫由����엯�쓣  紐낆떆�븯湲곗쐞�빐�꽌 TypeToken�쓣 �궗�슜
		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<MrpGatheringTO> mrpGatheringList = productionServiceFacade.getMrpGathering(mrpNoArr);
			
			map.put("gridRowJson", mrpGatheringList);
			map.put("errorCode", 1);
			map.put("errorMsg", "�꽦怨�");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
	
	

	public ModelAndView registerMrpGathering(HttpServletRequest request, HttpServletResponse response) {

		String mrpGatheringRegisterDate = request.getParameter("mrpGatheringRegisterDate"); //�꽑�깮�븳�궇吏�
		String mrpNoList = request.getParameter("mrpNoList");
		String mrpNoAndItemCodeList = request.getParameter("mrpNoAndItemCodeList");
		ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
				new TypeToken<ArrayList<String>>() { }.getType());
		HashMap<String, String> mrpNoAndItemCodeMap =  gson.fromJson(mrpNoAndItemCodeList, //mprNO : ItemCode 
              new TypeToken<HashMap<String, String>>() { }.getType());
		HashMap<String, Object> map = new HashMap<>();

		try {

			HashMap<String, Object> resultMap  = productionServiceFacade.registerMrpGathering(mrpGatheringRegisterDate, mrpNoArr,mrpNoAndItemCodeMap);	 
//														�꽑�깮�븳�궇吏�                  				getRowData		MRP-NO : DK-AP01
			
			
			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "�꽦怨�");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
	

	public ModelAndView searchMrpGathering(HttpServletRequest request, HttpServletResponse response) {

		String searchDateCondition = request.getParameter("searchDateCondition");
		String startDate = request.getParameter("mrpGatheringStartDate");
		String endDate = request.getParameter("mrpGatheringEndDate");
		
		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<MrpGatheringTO> mrpGatheringList = 
					productionServiceFacade.searchMrpGatheringList(searchDateCondition, startDate, endDate);
			
			map.put("gridRowJson", mrpGatheringList);
			map.put("errorCode", 1);
			map.put("errorMsg", "�꽦怨�");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}
	
	
}
