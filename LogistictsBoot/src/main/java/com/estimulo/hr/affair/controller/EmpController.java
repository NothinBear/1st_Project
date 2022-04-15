package com.estimulo.hr.affair.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.estimulo.hr.affair.serviceFacade.AffairServiceFacade;
import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeBasicTO;
import com.estimulo.hr.affair.to.EmployeeDetailTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;


@RestController
@RequestMapping(value = {"/hr/*", "/authorityManager/*"})
public class EmpController {
	@Autowired
	private AffairServiceFacade affairServiceFacade;
	private ModelMap modelMap = new ModelMap();
	
	// GSON �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚
	
	@PostMapping("/searchAllEmpList.do")
	public ModelMap searchAllEmpList(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String companyCode = request.getParameter("companyCode");
		String workplaceCode = request.getParameter("workplaceCode");
		String deptCode = request.getParameter("deptCode");

		ArrayList<EmpInfoTO> empList = null;
		String[] paramArray = null;

		try {

			switch (searchCondition) {

			case "ALL":

				paramArray = new String[] { companyCode };
				break;

			case "WORKPLACE":

				paramArray = new String[] { companyCode, workplaceCode };
				break;

			case "DEPT":

				paramArray = new String[] { companyCode, deptCode };
				break;

			case "RETIREMENT":

				paramArray = new String[] { companyCode };
				break;

			}

			empList = affairServiceFacade.getAllEmpList(searchCondition, paramArray);

			modelMap.put("gridRowJson", empList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	@GetMapping("/searchEmpInfo.do")
	public ModelMap searchEmpInfo(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String empCode = request.getParameter("empCode");

		EmpInfoTO empInfoTO = null;

		

		try {

			empInfoTO = affairServiceFacade.getEmpInfo(companyCode, empCode);

			modelMap.put("empInfo", empInfoTO);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;

	}
	@RequestMapping(value="/checkUserIdDuplication.do", method=RequestMethod.POST)
	public ModelMap checkUserIdDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newUserId = request.getParameter("newUseId");
		
		try {
			Boolean flag = affairServiceFacade.checkUserIdDuplication(companyCode, newUserId);

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
	@RequestMapping(value="/checkEmpCodeDuplication.do", method=RequestMethod.GET)
	public ModelMap checkEmpCodeDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = request.getParameter("newEmpCode");

		

		try {

			Boolean flag = affairServiceFacade.checkEmpCodeDuplication(companyCode, newEmpCode);

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
	@GetMapping("/getNewEmpCode.do")
	public ModelMap getNewEmpCode(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = null;
		
		try {

			newEmpCode = affairServiceFacade.getNewEmpCode(companyCode);

			modelMap.put("newEmpCode", newEmpCode);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping(value= "/batchListProcess.do", method=RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		String tableName = request.getParameter("tableName");

		try {

			ArrayList<EmployeeBasicTO> empBasicList = null;
			ArrayList<EmployeeDetailTO> empDetailList = null;
			ArrayList<EmployeeSecretTO> empSecretList = null;

			HashMap<String, Object> resultMap = null;

			if (tableName.equals("BASIC")) {

				empBasicList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeBasicTO>>() {
				}.getType());

				resultMap = affairServiceFacade.batchEmpBasicListProcess(empBasicList);

			} else if (tableName.equals("DETAIL")) {

				empDetailList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeDetailTO>>() {
				}.getType());
				System.out.println(gson.toJson(empDetailList));
				
				resultMap = affairServiceFacade.batchEmpDetailListProcess(empDetailList);

			} else if (tableName.equals("SECRET")) {

				empSecretList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeSecretTO>>() {
				}.getType());

				System.out.println(gson.toJson(empSecretList));

				
				resultMap = affairServiceFacade.batchEmpSecretListProcess(empSecretList);

			}

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
