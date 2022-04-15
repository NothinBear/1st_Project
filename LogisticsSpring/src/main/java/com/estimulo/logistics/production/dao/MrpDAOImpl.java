package com.estimulo.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.estimulo.logistics.production.to.MrpInsertInfoTO;
import com.estimulo.logistics.production.to.MrpTO;
import com.estimulo.logistics.production.to.OpenMrpTO;
import com.estimulo.system.common.dao.IBatisSupportDAO;


public class MrpDAOImpl extends IBatisSupportDAO implements MrpDAO {
   
   @SuppressWarnings("unchecked")
public ArrayList<MrpTO> selectMrpList(String mrpGatheringStatusCondition) { 
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpGatheringStatusCondition", mrpGatheringStatusCondition);
		return (ArrayList<MrpTO>) getSqlMapClientTemplate().queryForList("mrp.selectMrpList", map);
	}

   
   @SuppressWarnings("unchecked")
public ArrayList<MrpTO> selectMrpListAsDate(String dateSearchCondtion, String startDate, String endDate) { 
	   HashMap<String,String> params = new HashMap<>();
	   params.put("dateSearchCondtion",dateSearchCondtion);
	   params.put("startDate", startDate);
	   params.put("endDate", endDate);
	   return (ArrayList<MrpTO>) getSqlMapClientTemplate().queryForList("mrp.selectMrpList",params);
   }
   
   

   @SuppressWarnings("unchecked")
   public ArrayList<MrpTO> selectMrpListAsMrpGatheringNo(String mrpGatheringNo) { 
	   return (ArrayList<MrpTO>) getSqlMapClientTemplate().queryForList("mrp.selectMrpListAsMrpGatheringNo",mrpGatheringNo);
   }
   
   @Override
   public HashMap<String,Object> openMrp(String mpsNoList) {
	   HashMap<String,String> params=new HashMap<>();
	   params.put("mpsNoList", mpsNoList);
	   @SuppressWarnings("unchecked")
	   ArrayList<OpenMrpTO> openMrpList = (ArrayList<OpenMrpTO>)getSqlMapClientTemplate().queryForList("mrp.openMrp", params);
	   HashMap<String,Object> resultMap = new HashMap<>();
	   resultMap.put("gridRowJson", openMrpList);
	   resultMap.put("errorCode",params.get("ERROR_CODE"));
       resultMap.put("errorMsg", params.get("ERROR_MSG"));
	   return resultMap;
   }
   
   
   
   @Override
   public int selectMrpCount(String mrpRegisterDate) {
	   @SuppressWarnings("unchecked")
	   List<MrpTO> mrpTOList = getSqlMapClientTemplate().queryForList("mrp.selectMrpCount", mrpRegisterDate);
	   TreeSet<Integer> intSet = new TreeSet<>();
	   for(MrpTO bean : mrpTOList) {
	      String mrpNo = bean.getMrpNo();
	      int no = Integer.parseInt(mrpNo.substring(mrpNo.length()-3, mrpNo.length()));
	      intSet.add(no);
	   }
	   if (intSet.isEmpty()) {
	      return 1;
	   } else {
	      return intSet.pollLast() + 1;   // 媛��옣 �넂�� 踰덊샇 + 1
	   }
   }
   

   @Override
   public void insertMrp(MrpTO bean) {
	   getSqlMapClientTemplate().insert("mrp.insertMrp", bean);
   }
   

   @Override
   public void updateMrp(MrpTO bean) {
	   getSqlMapClientTemplate().update("mrp.updateMrp", bean);
   }
   

   @Override
   public void changeMrpGatheringStatus(String mrpNo, String mrpGatheringNo, String mrpGatheringStatus) {
		HashMap<String, String> map = new HashMap<>();
		map.put("mrpNo", mrpNo);
		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("mrpGatheringStatus", mrpGatheringStatus);

		getSqlMapClientTemplate().update("mrp.changeMrpGatheringStatus", map);
   }

   
   
   @Override
   public void deleteMrp(MrpTO bean) {
		getSqlMapClientTemplate().delete("mrp.deleteMrp", bean);   
   }

   @Override
	public HashMap<String, Object> insertMrpList(String mrpRegisterDate) {
	     HashMap<String,Object> resultMap = new HashMap<>();
	     HashMap<String,String> params=new HashMap<>();
	     params.put("mrpRegisterDate",mrpRegisterDate);
		 MrpInsertInfoTO mit = (MrpInsertInfoTO) getSqlMapClientTemplate().queryForObject("mrp.insertMrpList",params);
	     resultMap.put("firstMrpNo",mit.getFirstMrpNo());
	     resultMap.put("lastMrpNo", mit.getLastMrpNo());
	     resultMap.put("length", mit.getLength());
	     resultMap.put("errorCode",params.get("ERROR_CODE"));
	     resultMap.put("errorMsg", params.get("ERROR_MSG"));
	     return resultMap;
	}
   
}