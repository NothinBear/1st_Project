package com.estimulo.system.authorityManager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.estimulo.system.authorityManager.serviceFacade.AuthorityManagerServiceFacadeImpl;
import com.estimulo.system.authorityManager.to.AuthorityGroupTO;
import com.estimulo.system.authorityManager.to.EmployeeAuthorityTO;
import com.estimulo.system.common.exception.DataAccessException;
import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class AuthorityGroupController extends MultiActionController{
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(AuthorityGroupController.class);
	// gson
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	// SF참조변수 
	private static AuthorityManagerServiceFacade authorityManagerSF = AuthorityManagerServiceFacadeImpl.getInstance();

	
	public ModelAndView getUserAuthorityGroup(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityGroupController : getUserAuthorityGroup 시작");
		}
		
		String empCode = request.getParameter("empCode");
		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;
		
		try {
			
			out = response.getWriter();

			ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityManagerSF.getUserAuthorityGroup(empCode);
			
			map.put("gridRowJson", authorityGroupTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(map));
			out.close();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityGroupController : getUserAuthorityGroup 종료");
		}
		
		return null;
	}
	
	public ModelAndView getAuthorityGroup(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityGroupController : getAuthorityGroup 시작");
		}
		
		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;
		
		try {
			
			out = response.getWriter();
			
			ArrayList<AuthorityGroupTO> authorityGroupTOList = authorityManagerSF.getAuthorityGroup();
			
			map.put("gridRowJson", authorityGroupTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
			
		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());
			
		} finally {
			out.println(gson.toJson(map));
			out.close();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityGroupController : getAuthorityGroup 종료");
		}
		
		return null;
	}
	
	public ModelAndView insertEmployeeAuthorityGroup(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityGroupController : insertEmployeeAuthorityGroup 시작");
		}
		
		String empCode = request.getParameter("empCode");
		String insertDataList = request.getParameter("insertData");
		System.out.println(insertDataList); 
		ArrayList<EmployeeAuthorityTO> employeeAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<EmployeeAuthorityTO>>() {}.getType());
		
		HashMap<String, Object> map = new HashMap<>();  
		PrintWriter out = null;
		
		try {
			
			out = response.getWriter();
			
			authorityManagerSF.insertEmployeeAuthorityGroup(empCode, employeeAuthorityTOList);

			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
			
		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());
			
		} finally {
			out.println(gson.toJson(map));
			out.close();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("AuthorityGroupController : insertEmployeeAuthorityGroup 종료");
		} 
		
		return null;
	}
	
}
