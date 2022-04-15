package com.estimulo.system.base.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.estimulo.system.base.to.CodeDetailTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings("unchecked")
public class CodeDetailDAOImpl extends IBatisSupportDAO implements CodeDetailDAO {
	@Override
	public ArrayList<CodeDetailTO> selectDetailCodeList(String divisionCode) {
		return (ArrayList<CodeDetailTO>) getSqlMapClientTemplate().queryForList("codeDetail.selectDetailCodeList", divisionCode);
	}

	@Override
	public void insertDetailCode(CodeDetailTO bean) {
		getSqlMapClientTemplate().insert("codeDetail.insertDetailCode",bean);
	}

	@Override
	public void updateDetailCode(CodeDetailTO bean) {
		getSqlMapClientTemplate().update("codeDetail.updateDetailCode",bean);
	}

	@Override
	public void deleteDetailCode(CodeDetailTO bean) {
		getSqlMapClientTemplate().delete("codeDetail.deleteDetailCode",bean);
	}

	@Override
	public void changeCodeUseCheck(String divisionCodeNo, String detailCode, String codeUseCheck) {
		HashMap<String, String> map = new HashMap<>();
		
		map.put("divisionCodeNo",divisionCodeNo);
		map.put("detailCode",detailCode);
		map.put("codeUseCheck",codeUseCheck);
		
		getSqlMapClientTemplate().update("codeDetail.changeCodeUseCheck",map);
	}
}
