package com.estimulo.system.common.servlet.controller;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import com.estimulo.system.common.servlet.ModelAndView;


public class MultiActionController extends AbstractController{	//업무 관련은 여기서 실행, .do 하면 거의 여기에 다 들어온다.
	
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		
		//http://localhost:8282/SHRich/login.do?method=LoginCheak
	      
		
		  System.out.println("		@ MultiActionController handleRequestInternal");
	      String methodName = request.getParameter("method");
	      //loginCheck
	   
	      System.out.println("		@ methodName : "+methodName);
	      
	      
	      Class<?>[] parameters = new Class<?>[]{HttpServletRequest.class,HttpServletResponse.class};
          //   
	      //searchCompanyList
	      Class<?> cl = this.getClass();
	      	 //c1== ItemController
	      ModelAndView modelAndView=null;
	      try{        
	         modelAndView=(ModelAndView)cl.getMethod(methodName, parameters).invoke(this,request,response);
	         //모델앤드뷰= MemberLoginController의 LoginCheak 메서드실행.
	         // c1.getMehod() Method.invoke()
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      System.out.println("@@@@@@@@return null");
	      return modelAndView;
	   }
}
