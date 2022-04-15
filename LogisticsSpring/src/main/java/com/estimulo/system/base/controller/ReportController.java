package com.estimulo.system.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.system.base.serviceFacade.BaseServiceFacade;
import com.estimulo.system.base.to.ContractReportTO;
import com.estimulo.system.base.to.EstimateReportTO;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportController extends MultiActionController {

	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();
	
	private BaseServiceFacade baseServiceFacade;
	
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}

	
	public ModelAndView estimateReport(HttpServletRequest request, HttpServletResponse response) {
		String estimateNo = request.getParameter("orderDraftNo");
	   
		try {
			
			ArrayList<EstimateReportTO> estimateList = baseServiceFacade.getEstimateReport(estimateNo);
			//font와 encoding 에러가 나면 itextAsian jar파일을 maven 에 직접 주입해줘야됨
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(estimateList,false);
			modelMap.put("source", source);
			modelMap.put("format", "pdf");
			System.out.print(modelMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelAndView = new ModelAndView("estimatePdfView", modelMap);
		return modelAndView;
	}

	public ModelAndView contractReport(HttpServletRequest request, HttpServletResponse response) {

		String contractNo = request.getParameter("orderDraftNo");

		try {

			ArrayList<ContractReportTO> contractList = baseServiceFacade.getContractReport(contractNo);

			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(contractList);
			modelMap.put("source", source);
			modelMap.put("format", "pdf");
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		modelAndView = new ModelAndView("contractPdfView", modelMap);
		
		return modelAndView;
	}
}