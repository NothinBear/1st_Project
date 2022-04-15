package com.estimulo.system.base.dao;

import java.util.ArrayList;

import com.estimulo.system.base.to.CodeTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;

@SuppressWarnings({"unchecked"})
public class CodeDAOImpl extends IBatisSupportDAO implements CodeDAO {

	@Override
	public ArrayList<CodeTO> selectCodeList() {
		return (ArrayList<CodeTO>) getSqlMapClientTemplate().queryForList("code.selectCodeList");
	}

	@Override
	public void insertCode(CodeTO bean) {
		getSqlMapClientTemplate().insert("code.insertCode" ,bean);
	}

	@Override
	public void updateCode(CodeTO bean) {
		getSqlMapClientTemplate().update("updateCode", bean);
	}

	@Override
	public void deleteCode(CodeTO bean) {
		getSqlMapClientTemplate().delete("deleteCode", bean);
	}
}
