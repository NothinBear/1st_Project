package com.estimulo.logistics.production.mapper;

import java.util.ArrayList;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.production.to.MrpTO;

@Mapper
public interface MrpDAO {

	public ArrayList<MrpTO> selectMrpList(HashMap<String, String> param) ;
	
	public ArrayList<MrpTO> selectMrpListAsDate(HashMap<String, String> param); 

	public ArrayList<MrpTO> selectMrpListAsMrpGatheringNo(String mrpGatheringNo);
	
	public HashMap<String,Object> openMrp(HashMap<String, Object> params);
	
	public int selectMrpCount(String mrpRegisterDate);

	public void insertMrp(MrpTO TO);
	
	public void updateMrp(MrpTO TO);
	
	public void  changeMrpGatheringStatus(HashMap<String, String> map);
	
	public void deleteMrp(MrpTO TO);

	public ArrayList<MrpTO> insertMrpList(HashMap<String, Object> params);
	
}
