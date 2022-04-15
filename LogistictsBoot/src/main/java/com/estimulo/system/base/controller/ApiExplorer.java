package com.estimulo.system.base.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.json.XML;

public class ApiExplorer extends AbstractController {

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<>();
		BufferedReader br = null;
		String result = null;
		// gson 라이브러리
		Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
		try {
			String pageNo = request.getParameter("pageNo");
			String numOfRows = request.getParameter("numOfRows");
			String startCreateDt = request.getParameter("startCreateDt");
			String endCreateDt = request.getParameter("endCreateDt");
			
			  String urlstr =
			  "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"
			  +
			  "?ServiceKey=6zVMxmw7BgNKh8EHAoYcJoKLa7zUg%2FAh92wsksSjivt2VEvqGKG7%2BSRlFMhP7hgWU2kV238R04RoLOEAIiQizw%3D%3D"
			  + "&pageNo="+pageNo+"&numOfRows="+numOfRows+"&startCreateDt="+startCreateDt+
			  "&endCreateDt="+endCreateDt;
			 

			/*
			 * String urlstr =
			 * "http://apis.data.go.kr/1262000/SafetyNewsList/getCountrySafetyNewsList" +
			 * "?ServiceKey=6zVMxmw7BgNKh8EHAoYcJoKLa7zUg%2FAh92wsksSjivt2VEvqGKG7%2BSRlFMhP7hgWU2kV238R04RoLOEAIiQizw%3D%3D"
			 * + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows + "&startCreateDt=" +
			 * startCreateDt + "&endCreateDt=" + endCreateDt;
			 */

			URL url = new URL(urlstr);

			// System.out.println("url = "+url);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
			result = "";
			String line;
			while ((line = br.readLine()) != null) {
				result = result + line;
			}
			JSONObject xmlJSONObj = XML.toJSONObject(result.toString());
			map.put("gridRowJson", xmlJSONObj.toString());
			map.put("error_code", 0);
			map.put("error_msg", "성공");
		} catch (UnsupportedEncodingException e) {
			map.put("error-code", -1);
			map.put("error-msg", "내부서버오류");
			e.printStackTrace();
		}catch (Exception e) {
			map.put("error-code", -1);
			map.put("error-msg", "내부서버오류");
			e.printStackTrace();

		}
		return new ModelAndView("jsonView",map);
	}

}
