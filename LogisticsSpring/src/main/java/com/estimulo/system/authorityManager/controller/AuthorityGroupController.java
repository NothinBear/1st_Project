package com.estimulo.system.authorityManager.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class AuthorityGroupController extends MultiActionController{
	
	// gson
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	// SF참조변수 
	private AuthorityManagerServiceFacade authorityManagerServiceFacade;
	public void setAuthorityManagerServiceFacade(AuthorityManagerServiceFacade authorityManagerServiceFacade) {
		this.authorityManagerServiceFacade = authorityManagerServiceFacade;
	}
	
	public ModelAndView getUserAuthorityGroup(HttpServletRequest request, HttpServletResponse response) {
		
		String empCode = request.getParameter("empCode");
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			
			ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityManagerServiceFacade.getUserAuthorityGroup(empCode);
			
			map.put("gridRowJson", authorityGroupTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		}
		
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView getAuthorityGroup(HttpServletRequest request, HttpServletResponse response) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		try {
			
			ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityManagerServiceFacade.getAuthorityGroup();
			
			map.put("gridRowJson", authorityGroupTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
			
		}
		
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView insertEmployeeAuthorityGroup(HttpServletRequest request, HttpServletResponse response) {
		
		
		String empCode = request.getParameter("empCode");
		String insertDataList = request.getParameter("insertData");
		ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<EmployeeAuthorityTO>>() {}.getType());
		
		HashMap<String, Object> map = new HashMap<>();  
		
		try {
			
			authorityManagerServiceFacade.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);

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
