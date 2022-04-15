package com.estimulo.system.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.system.basicInfo.serviceFacade.BasicInfoServiceFacade;
import com.estimulo.system.basicInfo.to.DepartmentTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DepartmentController extends MultiActionController {

	// serviceFacade 참조변수 선언
	private BasicInfoServiceFacade basicInfoServiceFacade;
	public void setBasicInfoServiceFacade(BasicInfoServiceFacade basicInfoServiceFacade) {
		this.basicInfoServiceFacade = basicInfoServiceFacade;
	}

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	public ModelAndView searchDepartmentList(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");

		String companyCode = request.getParameter("companyCode");

		String workplaceCode = request.getParameter("workplaceCode");

		
		ArrayList<DepartmentTO> departmentList = null;

		HashMap<String, Object> map = new HashMap<>();

		try {

			departmentList = basicInfoServiceFacade.getDepartmentList(searchCondition, companyCode, workplaceCode);

			map.put("gridRowJson", departmentList);
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

		ArrayList<DepartmentTO> departmentList = gson.fromJson(batchList, new TypeToken<ArrayList<DepartmentTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();

		try {

			HashMap<String, Object> resultMap = basicInfoServiceFacade.batchDepartmentListProcess(departmentList);

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
