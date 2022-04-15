package com.estimulo.system.base.applicationService;

import java.util.ArrayList;

import com.estimulo.system.base.dao.AddressDAO;
import com.estimulo.system.base.to.AddressTO;

public class AddressApplicationServiceImpl implements AddressApplicationService {

	// DAO 참조변수
	private AddressDAO addressDAO;
	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue,
			String mainNumber) {

		ArrayList<AddressTO> addressList = null;

			String sidoCode = addressDAO.selectSidoCode(sidoName);

			switch (searchAddressType) {

			case "roadNameAddress":

				String buildingMainNumber = mainNumber;

				addressList = addressDAO.selectRoadNameAddressList(sidoCode, searchValue, buildingMainNumber);

				break;

			case "jibunAddress":

				String jibunMainAddress = mainNumber;

				addressList = addressDAO.selectJibunAddressList(sidoCode, searchValue, jibunMainAddress);

				break;

			}
 
		return addressList;

	}

}
