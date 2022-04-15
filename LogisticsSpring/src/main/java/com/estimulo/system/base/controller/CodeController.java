package com.estimulo.system.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.system.base.serviceFacade.BaseServiceFacade;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CodeController extends MultiActionController {

	// serviceFacade 참조변수 선언
	private BaseServiceFacade baseServiceFacade;
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView findCodeList(HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<>();

		try {
			ArrayList<CodeTO> codeList = baseServiceFacade.getCodeList();

			map.put("codeList", codeList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView findDetailCodeList(HttpServletRequest request, HttpServletResponse response) {

		String divisionCode = request.getParameter("divisionCodeNo");	//estimateRegister.jsp 에서 날아온값으 받아줌 divisionCode = "CL-01"
		
		HashMap<String, Object> map = new HashMap<>();

		try {

			ArrayList<CodeDetailTO> detailCodeList = baseServiceFacade.getDetailCodeList(divisionCode); 

			map.put("detailCodeList", detailCodeList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView checkCodeDuplication(HttpServletRequest request, HttpServletResponse response) {

		String divisionCode = request.getParameter("divisionCode");
		String newDetailCode = request.getParameter("newCode");

		HashMap<String, Object> map = new HashMap<>();
		try {

			Boolean flag = baseServiceFacade.checkCodeDuplication(divisionCode, newDetailCode);

			map.put("result", flag);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView",map);
	}

	public ModelAndView batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		String tableName = request.getParameter("tableName");

		HashMap<String, Object> map = new HashMap<>();

		try {
			ArrayList<CodeTO> codeList = null;
			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			if (tableName.equals("CODE")) {

				codeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeTO>>() {
				}.getType());
				//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
				resultMap = baseServiceFacade.batchCodeListProcess(codeList);

			} else if (tableName.equals("CODE_DETAIL")) {

				detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
				}.getType());
				//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
				resultMap = baseServiceFacade.batchDetailCodeListProcess(detailCodeList);

			}

			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} return new ModelAndView("jsonView",map);
	}

	
	public ModelAndView changeCodeUseCheckProcess(HttpServletRequest request, HttpServletResponse response) {
		
		String batchList = request.getParameter("batchList");

		HashMap<String, Object> map = new HashMap<>();

		try {
			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
			}.getType());
			//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
			resultMap = baseServiceFacade.changeCodeUseCheckProcess(detailCodeList);

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
