package com.estimulo.system.base.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelController extends MultiActionController{
	
	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	
	private ModelAndView modelAndView = null;
	
	public ModelAndView createExcel(HttpServletRequest request, HttpServletResponse response) {
		//.xls 확장자 지원
		/*
		 * HSSFWorkbook wb = null; HSSFSheet sheet = null; Row row = null; Cell cell =
		 * null;
		 */
		if (logger.isDebugEnabled()) {
	        logger.debug("ExcelController : createExcel 시작");
		}
				//.xlsx 확장자 지원
				XSSFWorkbook xssfWb = null; // .xlsx
				XSSFSheet xssfSheet = null; // .xlsx
				XSSFRow xssfRow = null; // .xlsx
				XSSFCell xssfCell = null;// .xlsx
				String excelName = request.getParameter("excelName");
				String data = request.getParameter("data");
				JSONObject json = new JSONObject(data);
				FileOutputStream fos1 = null;
				HashMap<String, Object> map = new HashMap<>();
				PrintWriter out2 = null;
				try {
					out2 = response.getWriter();
					
					int rowNo = 0; // 행 갯수 
					// 워크북 생성
					xssfWb = new XSSFWorkbook();
					xssfSheet = xssfWb.createSheet("엑셀 테스트"); // 워크시트 이름
					
					//헤더용 폰트 스타일
					XSSFFont font = xssfWb.createFont();
					font.setFontName(HSSFFont.FONT_ARIAL); //폰트스타일
					font.setFontHeightInPoints((short)14); //폰트크기
					font.setBold(true); //Bold 유무
					
					//테이블 타이틀 스타일
					CellStyle cellStyle_Title = xssfWb.createCellStyle();
					int columnPos=0;
					for(String key : json.keySet()) {
						System.out.println(key);
						xssfSheet.setColumnWidth(columnPos, (xssfSheet.getColumnWidth(columnPos))+(short)2048);
						columnPos++;
					}
					cellStyle_Title.setFont(font); // cellStle에 font를 적용
					cellStyle_Title.setAlignment(HorizontalAlignment.CENTER); // 정렬
					//셀병합
					xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnPos-1)); //첫행, 마지막행, 첫열, 마지막열( 0번째 행의 0~8번째 컬럼을 병합한다)
					//타이틀 생성
					xssfRow = xssfSheet.createRow(rowNo++); //행 객체 추가
					xssfCell = xssfRow.createCell((short) 0); // 추가한 행에 셀 객체 추가
					xssfCell.setCellStyle(cellStyle_Title); // 셀에 스타일 지정
					xssfCell.setCellValue(excelName+" 정보"); // 데이터 입력

					CellStyle cellStyle_Column = xssfWb.createCellStyle();
					cellStyle_Column.setBorderTop(BorderStyle.THIN); //테두리 위쪽
					cellStyle_Column.setBorderBottom(BorderStyle.THIN); //테두리 아래쪽
					cellStyle_Column.setBorderLeft(BorderStyle.THIN); //테두리 왼쪽
					cellStyle_Column.setBorderRight(BorderStyle.THIN); //테두리 오른쪽
					cellStyle_Column.setAlignment(HorizontalAlignment.CENTER);
					xssfRow = xssfSheet.createRow(rowNo++);
					columnPos=0;
					for(String key : json.keySet()) {
						xssfCell = xssfRow.createCell((short) columnPos);
						xssfCell.setCellStyle(cellStyle_Column);
						xssfCell.setCellValue(key);
						columnPos++;
					}
					
					  CellStyle cellStyle_Body = xssfWb.createCellStyle();
					  cellStyle_Body.setAlignment(HorizontalAlignment.LEFT);
					  
					  for(String key : json.keySet()) { 
						  System.out.print(key+" : ");
						  System.out.println(json.get(key)); xssfRow = xssfSheet.createRow(rowNo++);
						  xssfCell = xssfRow.createCell((short) columnPos);
						  xssfCell.setCellStyle(cellStyle_Body); xssfCell.setCellValue(""); 
						  xssfRow = xssfSheet.createRow(rowNo++);
					  }
					 
					  //첫행,마지막행,첫열,마지막열
						//헤더 01 
						/*
						 * xssfCell = xssfRow.createCell((short) 8);
						 * xssfCell.setCellStyle(cellStyle_Body); xssfCell.setCellValue("헤더01 셀08");
						 * xssfRow = xssfSheet.createRow(rowNo++); //헤더 02 xssfCell
						 * =xssfRow.createCell((short) 0); xssfCell.setCellStyle(cellStyle_Body);
						 * xssfCell.setCellValue("헤더02 셀01"); xssfCell = xssfRow.createCell((short) 8);
						 * xssfCell.setCellStyle(cellStyle_Body); xssfCell.setCellValue("헤더02 셀08");
						 * xssfRow = xssfSheet.createRow(rowNo++); //헤더 03 xssfCell =
						 * xssfRow.createCell((short) 0); xssfCell.setCellStyle(cellStyle_Body);
						 * xssfCell.setCellValue("헤더03 셀01"); xssfCell = xssfRow.createCell((short) 8);
						 * xssfCell.setCellStyle(cellStyle_Body); xssfCell.setCellValue("헤더03 셀08");
						 * xssfRow = xssfSheet.createRow(rowNo++); //헤더 04 xssfCell
						 * =xssfRow.createCell((short) 0); xssfCell.setCellStyle(cellStyle_Body);
						 * xssfCell.setCellValue("헤더04 셀01"); xssfCell = xssfRow.createCell((short) 8);
						 * xssfCell.setCellStyle(cellStyle_Body); xssfCell.setCellValue("헤더04 셀08");
						 */
					 
					 
					
					String localFile = request.getSession().getServletContext().getRealPath("\\resources\\excel\\"+excelName+".xlsx").toString();
					
					File lfile = new File(localFile);
					
					fos1 = new FileOutputStream(lfile);
					xssfWb.write(fos1);
					
					if (xssfWb != null)	xssfWb.close();
					
					
					//ctx.put("FILENAME", "입고상세출력_"+ mapList.get(0).get("PRINT_DATE"));
					//if(file != null) file.deleteOnExit();
					}
				catch(FileNotFoundException e1) {
					map.put("errorCode", -1);
					map.put("errorMsg", "파일이 열러있거나 중복된 파일이 있습니다.");
					out2.println(gson.toJson(map));
					out2.close();
				}
				catch(Exception e){
					map.put("errorCode", -1);
					map.put("errorMsg", e.getMessage());
					e.printStackTrace();
				}finally{
					out2.println(gson.toJson(map));
					out2.close();
					if (logger.isDebugEnabled()) {
					       logger.debug("ExcelController : createExcel 종료");
					 }
					if (fos1 != null)
						try {
							fos1.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			}
		return modelAndView;
	}
}