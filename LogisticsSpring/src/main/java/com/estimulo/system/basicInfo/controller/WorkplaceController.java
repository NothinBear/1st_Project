package com.estimulo.system.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.system.basicInfo.serviceFacade.BasicInfoServiceFacade;
import com.estimulo.system.basicInfo.to.WorkplaceTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WorkplaceController extends MultiActionController {
	// serviceFacade 참조변수 선언
	private BasicInfoServiceFacade basicInfoServiceFacade;
	public void setBasicInfoServiceFacade(BasicInfoServiceFacade basicInfoServiceFacade) {
		this.basicInfoServiceFacade = basicInfoServiceFacade;
	}
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	public ModelAndView searchWorkplaceList(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");

		ArrayList<WorkplaceTO> workplaceList = null;

		HashMap<String, Object> map = new HashMap<>();

		try {

			workplaceList = basicInfoServiceFacade.getWorkplaceList(companyCode);

			map.put("gridRowJson", workplaceList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView", map);
	}

	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<WorkplaceTO> workplaceList = gson.fromJson(batchList, new TypeToken<ArrayList<WorkplaceTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();

		try {

			HashMap<String, Object> resultMap = basicInfoServiceFacade.batchWorkplaceListProcess(workplaceList);

			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView", map);
	}
}
