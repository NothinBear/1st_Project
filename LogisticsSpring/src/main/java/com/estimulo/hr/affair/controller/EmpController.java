package com.estimulo.hr.affair.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.hr.affair.serviceFacade.AffairServiceFacade;
import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.hr.affair.to.EmployeeBasicTO;
import com.estimulo.hr.affair.to.EmployeeDetailTO;
import com.estimulo.hr.affair.to.EmployeeSecretTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class EmpController extends MultiActionController {

	private AffairServiceFacade affairServiceFacade;
	
	public void setAffairServiceFacade(AffairServiceFacade affairServiceFacade) {
		this.affairServiceFacade = affairServiceFacade;
	}
	// GSON �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚

	public ModelAndView searchAllEmpList(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String companyCode = request.getParameter("companyCode");
		String workplaceCode = request.getParameter("workplaceCode");
		String deptCode = request.getParameter("deptCode");

		ArrayList<EmpInfoTO> empList = null;
		String[] paramArray = null;

		HashMap<String, Object> map = new HashMap<>();

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

			map.put("gridRowJson", empList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView", map);
	}

	public ModelAndView searchEmpInfo(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String empCode = request.getParameter("empCode");

		EmpInfoTO empInfoTO = null;

		HashMap<String, Object> map = new HashMap<>();

		try {

			empInfoTO = affairServiceFacade.getEmpInfo(companyCode, empCode);

			map.put("empInfo", empInfoTO);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView", map);

	}

	public ModelAndView checkUserIdDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newUserId = request.getParameter("newUseId");
		
		HashMap<String, Object> map = new HashMap<>();

		try {
			Boolean flag = affairServiceFacade.checkUserIdDuplication(companyCode, newUserId);

			map.put("result", flag);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		return new ModelAndView("jsonView", map);
	}

	public ModelAndView checkEmpCodeDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = request.getParameter("newEmpCode");

		HashMap<String, Object> map = new HashMap<>();

		try {

			Boolean flag = affairServiceFacade.checkEmpCodeDuplication(companyCode, newEmpCode);

			map.put("result", flag);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} 

		return new ModelAndView("jsonView", map);
	}

	public ModelAndView getNewEmpCode(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = null;
		
		HashMap<String, Object> map = new HashMap<>();

		try {

			newEmpCode = affairServiceFacade.getNewEmpCode(companyCode);

			map.put("newEmpCode", newEmpCode);
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
		String tableName = request.getParameter("tableName");

		HashMap<String, Object> map = new HashMap<>();

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
