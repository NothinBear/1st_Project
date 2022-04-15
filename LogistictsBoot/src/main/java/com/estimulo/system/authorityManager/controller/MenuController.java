package com.estimulo.system.authorityManager.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RestController
@RequestMapping("/authorityManager/*")
public class MenuController {
	
	// gson
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	// SF참조변수 
	@Autowired
	private AuthorityManagerServiceFacade authorityManagerServiceFacade;
	private ModelMap modelMap = new ModelMap();

	@RequestMapping(value="/insertMenuAuthority.do", method=RequestMethod.GET)
	public ModelMap insertMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		String insertDataList = request.getParameter("insertData");
		ArrayList<MenuAuthorityTO> menuAuthorityTOList = gson.fromJson(insertDataList,
				new TypeToken<ArrayList<MenuAuthorityTO>>() {}.getType());
		
		try {
			
			authorityManagerServiceFacade.insertMenuAuthority(authorityGroupCode, menuAuthorityTOList);
			
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("errorCode", -1);
			modelMap.put("errorMsg", e1.getMessage());
		}
		
		return modelMap;
	}
	@RequestMapping(value="/getMenuAuthority.do", method=RequestMethod.GET)
	public ModelMap getMenuAuthority(HttpServletRequest request, HttpServletResponse response) {
		
		String authorityGroupCode = request.getParameter("authorityGroupCode");
		
		try {
			
			ArrayList<MenuAuthorityTO> menuAuthorityTOList = authorityManagerServiceFacade.getMenuAuthority(authorityGroupCode);
			
			modelMap.put("gridRowJson", menuAuthorityTOList);
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
