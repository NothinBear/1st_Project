package com.estimulo.system.common.servlet.context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.estimulo.system.common.servlet.controller.Controller;


public class ApplicationContext  {
	private HashMap<String,Object> beanNameMap;
	private HashMap<String,HashMap<String,Object>> categoryMap;
	
	public ApplicationContext(ServletConfig config, ServletContext application){
		System.out.println("		@ ApplicationContext");
		try {
			String configFile=config.getInitParameter("contextConfigLocation"); // /WEB-INF/contextConfigLocation/properties/sales.properties
			String[] commandFiles=configFile.split(",");
			System.out.println("		@ contextConfigLocation:"+commandFiles.toString());
			categoryMap=new HashMap<String,HashMap<String,Object>>();
			for(String command : commandFiles) {
				String keys=command.substring(command.lastIndexOf("/")+1); // sales.properties
				String key=keys.split("[.]")[0]; // sales (category)
				beanNameMap = new HashMap<String,Object>();
				categoryMap.put(key, beanNameMap);  // category - beanNameMap(비어있는 HashMap)
				setCommands(config,application,beanNameMap,command);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// beanNameMap 세팅
	public void setCommands(ServletConfig config, ServletContext application, HashMap<String,Object> beanNameMap, String command) {
		String filename=application.getRealPath(command); // properties 파일 절대경로
		Properties properties=new Properties();		
		try {
			properties.load(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<String> set=properties.stringPropertyNames(); // beanName들
		for(String name: set){
			String beanName=name.trim();
			String className=properties.getProperty(beanName).trim(); 
			Object controller=null;
			try {
				controller = Class.forName(className).newInstance();		
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			beanNameMap.put(beanName, controller);  
		}
	}
	
	public Controller getBean(String category, String beanName){
		System.out.println("		@ TO : "+beanName);
		//System.out.println("		@beanNameMap : "+category)		
		HashMap<String,Object> beanNameMap=categoryMap.get(category); // sales : { beanNameMap }
		return (Controller)beanNameMap.get(beanName); 
	}
}