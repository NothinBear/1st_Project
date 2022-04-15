package com.estimulo.system.authorityManager.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MenuController extends MultiActionController{
	
	// gson
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	// SF참조변수 
	private AuthorityManagerServiceFacade authorityManagerServiceFacade;
	public void setAuthorityManagerServiceFacade(AuthorityManagerServiceFacade authorityManagerServiceFacade) {
		this.authorityManagerServiceFacade = authorityManagerServiceFacade;
	}
	
	
	public ModelAndView insertMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		String insertDataList = request.getParameter("insertData");
		ArrayList<MenuAuthorityTO> menuAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<MenuAuthorityTO>>() {}.getType());
		
		HashMap<String, Object> map = new HashMap<>();  
		
		try {
			
			authorityManagerServiceFacade.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
			
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
			
		}
		
		return new ModelAndView("jsonView",map);
	}
	
	public ModelAndView getMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		
		HashMap<String, Object> map = new HashMap<>();  
		
		try {
			
			ArrayList<MenuAuthorityTO> menuAuthorityTOList = authorityManagerServiceFacade.getMenuAuthority(authorityGroupCode);
			
			map.put("gridRowJson", menuAuthorityTOList);
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
