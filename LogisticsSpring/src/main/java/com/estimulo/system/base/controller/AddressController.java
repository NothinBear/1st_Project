package com.estimulo.system.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.estimulo.system.base.serviceFacade.BaseServiceFacade;
import com.estimulo.system.base.to.AddressTO;

public class AddressController extends MultiActionController {

	// serviceFacade 참조변수 선언
	private BaseServiceFacade baseServiceFacade;
	public void setBaseServiceFacade(BaseServiceFacade baseServiceFacade) {
		this.baseServiceFacade = baseServiceFacade;
	}
	// GSON 라이브러리F

	public ModelAndView searchAddressList(HttpServletRequest request, HttpServletResponse response) {

		String sidoName = request.getParameter("sidoName");
		String searchAddressType = request.getParameter("searchAddressType");
		String searchValue = request.getParameter("searchValue");
		String mainNumber = request.getParameter("mainNumber");

		HashMap<String, Object> map = new HashMap<>();

		try {
			ArrayList<AddressTO> addressList = baseServiceFacade.getAddressList(sidoName, searchAddressType, searchValue,
					mainNumber);

			map.put("addressList", addressList);
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
