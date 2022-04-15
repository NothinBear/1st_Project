package com.estimulo.system.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.estimulo.system.base.serviceFacade.BaseServiceFacade;
import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.base.to.CodeTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/base/*")
public class CodeController  {

	// serviceFacade 참조변수 선언
	@Autowired
	private BaseServiceFacade baseServiceFacade;
	private ModelMap modelMap=new ModelMap();
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping("/searchCodeList.do")
	public ModelMap findCodeList(HttpServletRequest request, HttpServletResponse response) {

		try {
			ArrayList<CodeTO> codeList = baseServiceFacade.getCodeList();

			modelMap.put("codeList", codeList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping(value = "/codeList.do", method = RequestMethod.POST)
	public ModelMap findDetailCodeList(HttpServletRequest request, HttpServletResponse response) {

		String divisionCode = request.getParameter("divisionCodeNo");	//estimateRegister.jsp 에서 날아온값으 받아줌 divisionCode = "CL-01"
		try {

			ArrayList<CodeDetailTO> detailCodeList = baseServiceFacade.getDetailCodeList(divisionCode); 

			modelMap.put("detailCodeList", detailCodeList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());
		} 
		return modelMap;
	}
	@RequestMapping("/checkCodeDeuplication.do")
	public ModelMap checkCodeDuplication(HttpServletRequest request, HttpServletResponse response) {

		String divisionCode = request.getParameter("divisionCode");
		String newDetailCode = request.getParameter("newCode");

		try {

			Boolean flag = baseServiceFacade.checkCodeDuplication(divisionCode, newDetailCode);

			modelMap.put("result", flag);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	@RequestMapping("/batchListProcess.do")
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		String tableName = request.getParameter("tableName");


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

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} return modelMap;
	}

	@RequestMapping("/changeCodeUseCheckProcess.do")
	public ModelMap changeCodeUseCheckProcess(HttpServletRequest request, HttpServletResponse response) {
		
		String batchList = request.getParameter("batchList");


		try {
			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
			}.getType());
			//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용
			resultMap = baseServiceFacade.changeCodeUseCheckProcess(detailCodeList);

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
	
}
