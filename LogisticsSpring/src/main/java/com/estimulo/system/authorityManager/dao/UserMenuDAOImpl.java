package com.estimulo.system.authorityManager.dao;

import java.util.HashMap;
import java.util.List;

import com.estimulo.system.authorityManager.to.UserMenuTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class UserMenuDAOImpl extends IBatisSupportDAO implements UserMenuDAO {

	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode, String positionCode) {
		HashMap<String, String> map = new HashMap<>();

		map.put("workplaceCode", workplaceCode);
		map.put("deptCode", deptCode);
		map.put("positionCode", positionCode);

		HashMap<String, UserMenuTO> userMenuTOMap = new HashMap<>();

		List<UserMenuTO> list = getSqlMapClientTemplate().queryForList("userMenu.selectUserMenuCodeList", map);

		for (UserMenuTO bean : list) {
			userMenuTOMap.put(bean.getNo() + "", bean);
		}
		return userMenuTOMap;
	}
}
