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
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;
import com.estimulo.system.common.exception.DataAccessException;
import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MenuController extends MultiActionController{
	
	//logger
	private static Logger logger = LoggerFactory.getLogger(AuthorityGroupController.class);
	// gson
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	// SF참조변수 
	AuthorityManagerServiceFacade authorityManagerSF = AuthorityManagerServiceFacadeImpl.getInstance();
	
	
	public ModelAndView insertMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MenuController : insertMenuAuthority 시작");
		}
		
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		String insertDataList = request.getParameter("insertData");
		System.out.println(insertDataList); 
		ArrayList<MenuAuthorityTO> menuAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<MenuAuthorityTO>>() {}.getType());
		
		HashMap<String, Object> map = new HashMap<>();  
		PrintWriter out = null;
		
		try {
			
			out = response.getWriter();
			
			authorityManagerSF.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
			
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
			logger.debug("MenuController : insertMenuAuthority 종료");
		} 
		
		return null;
	}
	
	public ModelAndView getMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MenuController : getMenuAuthority 시작");
		}
		
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		
		HashMap<String, Object> map = new HashMap<>();  
		PrintWriter out = null;
		
		try {
			
			out = response.getWriter();
			
			ArrayList<MenuAuthorityTO> menuAuthorityTOList = authorityManagerSF.getMenuAuthority(authorityGroupCode);
			
			map.put("gridRowJson", menuAuthorityTOList);
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
			logger.debug("MenuController : getMenuAuthority 종료");
		} 
		
		return null;
	}
}
