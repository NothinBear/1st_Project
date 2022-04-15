package com.estimulo.system.base.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.estimulo.system.common.sl.ServiceLocator;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportController extends MultiActionController {

   // SLF4J logger
   private static Logger logger = LoggerFactory.getLogger(ReportController.class);

   // iReport jrxml , jasper 파일저장 경로
   

   // 커넥션 풀 이름
   private static String connectionPoolName = "jdbc/logi";

   private ModelAndView modelAndView = null;

   public ModelAndView estimateReport(HttpServletRequest request, HttpServletResponse response) {
	   String iReportFolderPath=request.getSession().getServletContext().getRealPath("\\resources\\iReportForm").toString();
	   System.out.println(iReportFolderPath);
      if (logger.isDebugEnabled()) {
         logger.debug("ReportController : estimateReport 시작");
      }
//      JasperPrint jasper;
      HashMap<String, Object> parameters = new HashMap<>();
      JasperPrint  JasperPrint1=null; 
        JasperPrint jasperPrint2 = null;
      // 레포트 이름
      String reportName = "\\Estimate.jrxml";
      InputStream inputStream=null;
      ServletOutputStream out=null;
      try {
         response.setCharacterEncoding("utf-8");
         String orderDraftNo = request.getParameter("orderDraftNo"); //견적일련번호
         parameters.put("orderDraftNo", orderDraftNo);
         parameters.put("iReportFolderPath", iReportFolderPath.toString());
         DataSource dataSource = ServiceLocator.getInstance().getDataSource(connectionPoolName);
         Connection conn = dataSource.getConnection();
         inputStream = new FileInputStream(iReportFolderPath + reportName);

         JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
         JasperReport jasperReport1 = JasperCompileManager.compileReport(jasperDesign);
         JasperReport jasperReport2 = JasperCompileManager.compileReport(jasperDesign);
         JasperPrint1 = JasperFillManager.fillReport(jasperReport1, parameters, conn);
         jasperPrint2  = JasperFillManager.fillReport(jasperReport2, parameters, conn);
         out = response.getOutputStream();
         response.setContentType("application/pdf");
         JasperExportManager.exportReportToPdfStream(JasperPrint1, out);
          JasperExportManager.exportReportToPdfFile(jasperPrint2, iReportFolderPath+"\\pdf\\"+orderDraftNo+".pdf" );
         // Desktop.getDesktop().open(new File(iReportFolderPath+"\\pdf\\"+orderDraftNo+".pdf"));
         out.println();
         out.flush();  

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
    	  if(inputStream!=null) {
    		  try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }
    	  if(out!=null) {
    		  try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }
      }

      if (logger.isDebugEnabled()) {
         logger.debug("ReportController : estimateReport 종료");
      }
      return modelAndView;
   }

   
   public ModelAndView contractReport(HttpServletRequest request, HttpServletResponse response) {
	   String iReportFolderPath=request.getSession().getServletContext().getRealPath("\\resources\\iReportForm").toString();
      if (logger.isDebugEnabled()) {
         logger.debug("ReportController : contractReport 시작");
      }

      HashMap<String, Object> parameters = new HashMap<>();

      // 레포트 이름
      String reportName = "\\Contract.jrxml";
      InputStream inputStream=null;
      ServletOutputStream out=null;
      try {
    	  
         response.setCharacterEncoding("utf-8");
         String orderDraftNo = request.getParameter("orderDraftNo");
         parameters.put("orderDraftNo", orderDraftNo);
         parameters.put("iReportFolderPath", iReportFolderPath.toString());
         DataSource dataSource = ServiceLocator.getInstance().getDataSource(connectionPoolName);
         Connection conn = dataSource.getConnection();
         
         
         inputStream = new FileInputStream(iReportFolderPath + reportName);
         //jrxml 형식으로 읽어옴
         JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
         //jrxml 을 내가 원하는 모양을 가지고옴
         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
         //그 틀에 맞춰서 파라메터의 정보를 넣어줌
         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
         
         out = response.getOutputStream();
         response.setContentType("application/pdf");
         JasperExportManager.exportReportToPdfStream(jasperPrint, out);
         out.println();
         out.flush();         

      } catch (Exception e) {

         e.printStackTrace();
      } finally {
    	  if(inputStream!=null) {
    		  try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }
    	  if(out!=null) {
    		  try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }
      }

      if (logger.isDebugEnabled()) {
         logger.debug("ReportController : contractReport 종료");
      }
      return modelAndView;
   }
}