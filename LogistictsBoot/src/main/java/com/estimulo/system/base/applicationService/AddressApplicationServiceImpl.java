package com.estimulo.system.base.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.system.base.mapper.AddressDAO;
import com.estimulo.system.base.to.AddressTO;

@Component
public class AddressApplicationServiceImpl implements AddressApplicationService {

	// DAO 참조변수
	@Autowired
	private AddressDAO addressDAO;

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue,
			String mainNumber) {

		ArrayList<AddressTO> addressList = null;
		HashMap<String,String> param = null;
		
			String sidoCode = addressDAO.selectSidoCode(sidoName);

			switch (searchAddressType) {

			case "roadNameAddress":

				String buildingMainNumber = mainNumber;
				param = new HashMap<>();
				
				param.put("sidocode", sidoCode);
				param.put("searchValue", searchValue);
				param.put("buildingMainNumber", buildingMainNumber);
				addressList = addressDAO.selectRoadNameAddressList(param);

				break;

			case "jibunAddress":

				String jibunMainAddress = mainNumber;
				param = new HashMap<>();

				param.put("sidocode", sidoCode);
				param.put("searchValue", searchValue);
				param.put("jibunMainAddress", jibunMainAddress);
				addressList = addressDAO.selectJibunAddressList(param);

				break;

			}
 
		return addressList;

	}

}
