package com.estimulo.logistics.material.controller;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.logistics.material.serviceFacade.MaterialServiceFacade;
import com.estimulo.logistics.material.to.BomDeployTO;
import com.estimulo.logistics.material.to.BomInfoTO;
import com.estimulo.logistics.material.to.BomTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/material/*")
public class BomController  {
	@Autowired
	private MaterialServiceFacade purchaseSF;
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	ModelMap modelMap = new ModelMap();
	
	@RequestMapping(value="/searchBomDeploy.do", method=RequestMethod.POST)
	public ModelMap searchBomDeploy(HttpServletRequest request, HttpServletResponse response) {

		String deployCondition = request.getParameter("deployCondition");
		String itemCode = request.getParameter("itemCode");
		String itemClassificationCondition = request.getParameter("itemClassificationCondition");

		try {
			ArrayList<BomDeployTO> bomDeployList = purchaseSF.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);

			modelMap.put("gridRowJson", bomDeployList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping(value= "/searchBomInfo.do", method=RequestMethod.POST)
	public ModelMap searchBomInfo(HttpServletRequest request, HttpServletResponse response) {


		String parentItemCode = request.getParameter("parentItemCode");


		try {
			ArrayList<BomInfoTO> bomInfoList = purchaseSF.getBomInfoList(parentItemCode);

			modelMap.put("gridRowJson", bomInfoList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		
		return modelMap;

	}
	@RequestMapping(value ="/searchAllItemWithBomRegisterAvailable.do" ,method=RequestMethod.POST)
	public ModelMap searchAllItemWithBomRegisterAvailable(HttpServletRequest request,
			HttpServletResponse response) {



		try {
			ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = purchaseSF.getAllItemWithBomRegisterAvailable();

			modelMap.put("gridRowJson", allItemWithBomRegisterAvailable);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		} 
		
		return modelMap;

	}
	@RequestMapping(value= "/batchBomListProcess.do", method=RequestMethod.POST)
	public ModelMap batchBomListProcess(HttpServletRequest request, HttpServletResponse response) {


		String batchList = request.getParameter("batchList");
		// System.out.println(batchList);
		ArrayList<BomTO> batchBomList = gson.fromJson(batchList, new TypeToken<ArrayList<BomTO>>() {
		}.getType());
		//제너릭 클래스를 사용할경우 정해지지 않은 제너릭타입을  명시하기위해서 TypeToken을 사용

		try {

			HashMap<String, Object> resultList = purchaseSF.batchBomListProcess(batchBomList);

			modelMap.put("result", resultList);
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
