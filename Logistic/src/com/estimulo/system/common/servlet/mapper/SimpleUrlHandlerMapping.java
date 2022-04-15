package com.estimulo.system.common.servlet.mapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.estimulo.system.common.servlet.context.ApplicationContext;
import com.estimulo.system.common.servlet.controller.Controller;
import com.estimulo.system.common.servlet.mapper.SimpleUrlHandlerMapping;

public class SimpleUrlHandlerMapping {
	String category;
	private static SimpleUrlHandlerMapping instance;
	private HashMap<String, String> beanNames;
	
	public static SimpleUrlHandlerMapping getInstance(ServletContext application) {
		if (instance == null) {
			instance = new SimpleUrlHandlerMapping(application);
			System.out.println("		@ SimpleUrlHandlerMapping");
		}
		return instance;
	}

	private SimpleUrlHandlerMapping(ServletContext application) {
		beanNames = new HashMap<String, String>();
		String filename = application.getRealPath(application.getInitParameter("urlmappingFile"));
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			beanNames.put(key, value);
		}
	}

	public Controller getController(ApplicationContext applicationContext, HttpServletRequest request) {
		String rUri = request.getRequestURI(); 				// uri = /sales/estimateRegister.html : 배열개수 3
		System.out.println("		@ ruri : "+rUri);			// uri = /viewname : 배열개수2 (main)
		String cPath = request.getContextPath();
		System.out.println(rUri.substring(cPath.length()));
		String uri=rUri.substring(cPath.length());
		String beanName = beanNames.get(uri); 	  			// beanName="urlFilenameViewController"
		System.out.println("		@ TOName : "+beanName);
		if((uri.split("/")).length==2) {
			category="main";
		}else {
			category=uri.split("/")[1]; // sales 
		}
		System.out.println("		@ category : "+category);
		return applicationContext.getBean(category,beanName);
	}
}
