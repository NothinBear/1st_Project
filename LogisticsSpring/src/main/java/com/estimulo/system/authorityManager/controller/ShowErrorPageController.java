package com.estimulo.system.authorityManager.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


public class ShowErrorPageController extends MultiActionController {

	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

		String viewName = "redirect:" + request.getContextPath() + "/hello.html";

		HashMap<String, Object> model = new HashMap<String, Object>();

		if (request.getRequestURI().contains("accessDenied")) {
			model.put("errorCode", -1);
			model.put("errorTitle", "Access Denied");
			model.put("errorMsg", "액세스 거부되었습니다");
			viewName = "errorPage";
		}

		ModelAndView modelAndView = new ModelAndView(viewName, model);
		
		return modelAndView;
	}

}
