package com.estimulo.system.base.applicationService;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.base.dao.AddressDAO;
import com.estimulo.system.base.dao.AddressDAOImpl;
import com.estimulo.system.base.to.AddressTO;
import com.estimulo.system.common.exception.DataAccessException;

public class AddressApplicationServiceImpl implements AddressApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(AddressApplicationServiceImpl.class);

	// 싱글톤
	private static AddressApplicationService instance = new AddressApplicationServiceImpl();

	private AddressApplicationServiceImpl() {
	}

	public static AddressApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ AddressApplicationServiceImpl 객체접근");
		}

		return instance;
	}

	// DAO 참조변수
	private static AddressDAO addressDAO = AddressDAOImpl.getInstance();

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue,
			String mainNumber) {

		if (logger.isDebugEnabled()) {
			logger.debug("AddressApplicationServiceImpl : getAddressList 시작");
		}

		ArrayList<AddressTO> addressList = null;

		try {

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

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("AddressApplicationServiceImpl : getAddressList 종료");
		}
		return addressList;

	}

}
