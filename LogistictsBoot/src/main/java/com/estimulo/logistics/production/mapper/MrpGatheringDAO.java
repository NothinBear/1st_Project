package com.estimulo.logistics.production.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.production.to.MrpGatheringTO;
@Mapper
public interface MrpGatheringDAO {

	public ArrayList<MrpGatheringTO> getMrpGathering(String mrpNoList);
	
	public ArrayList<MrpGatheringTO> selectMrpGatheringList(HashMap<String, String> parameter);
	
	public List<MrpGatheringTO> selectMrpGatheringCount(String mrpGatheringRegisterDate);
	
	public void insertMrpGathering(MrpGatheringTO TO);
	
	public void updateMrpGathering(MrpGatheringTO TO);
	
	public void deleteMrpGathering(MrpGatheringTO TO);

	public void updateMrpGatheringContract(HashMap<String, String> parameter);
	
	public int getMGSeqNo();
	
}
