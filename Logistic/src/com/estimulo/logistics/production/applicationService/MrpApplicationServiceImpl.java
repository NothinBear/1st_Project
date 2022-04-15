package com.estimulo.logistics.production.applicationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.logistics.production.dao.MpsDAO;
import com.estimulo.logistics.production.dao.MpsDAOImpl;
import com.estimulo.logistics.production.dao.MrpDAO;
import com.estimulo.logistics.production.dao.MrpDAOImpl;
import com.estimulo.logistics.production.dao.MrpGatheringDAO;
import com.estimulo.logistics.production.dao.MrpGatheringDAOImpl;
import com.estimulo.logistics.production.to.MrpGatheringTO;
import com.estimulo.logistics.production.to.MrpTO;
import com.estimulo.system.common.exception.DataAccessException;

public class MrpApplicationServiceImpl implements MrpApplicationService {

   // SLF4J logger
   private static Logger logger = LoggerFactory.getLogger(MrpApplicationServiceImpl.class);

   // 싱글톤
   private static MrpApplicationService instance = new MrpApplicationServiceImpl();

   private MrpApplicationServiceImpl() {
   }

   public static MrpApplicationService getInstance() {

      if (logger.isDebugEnabled()) {
         logger.debug("@ MrpApplicationServiceImpl 객체접근");
      }

      return instance;
   }

   // DAO 참조변수 선언
   private static MpsDAO mpsDAO = MpsDAOImpl.getInstance();
   private static MrpDAO mrpDAO = MrpDAOImpl.getInstance();
   private static MrpGatheringDAO mrpGatheringDAO = MrpGatheringDAOImpl.getInstance();

   public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpList 시작");
      }

      ArrayList<MrpTO> mrpList = null;

      try {

         mrpList = mrpDAO.selectMrpList(mrpGatheringStatusCondition);

      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpList 종료");
      }
      return mrpList;
   }

   public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpList 시작");
      }

      ArrayList<MrpTO> mrpList = null;

      try {

         mrpList = mrpDAO.selectMrpList(dateSearchCondtion, startDate, endDate);

      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpList 종료");
      }

      return mrpList;

   }

   public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpListAsMrpGatheringNo 시작");
      }

      ArrayList<MrpTO> mrpList = null;

      try {

         mrpList = mrpDAO.selectMrpListAsMrpGatheringNo(mrpGatheringNo);

      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpListAsMrpGatheringNo 종료");
      }
      return mrpList;

   }

   public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
         String endDate) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpGatheringList 시작");
      }

      ArrayList<MrpGatheringTO> mrpGatheringList = null;

      try {

         mrpGatheringList = mrpGatheringDAO.selectMrpGatheringList(dateSearchCondtion, startDate, endDate);

         for(MrpGatheringTO bean : mrpGatheringList)    {
            
            bean.setMrpTOList(  mrpDAO.selectMrpListAsMrpGatheringNo( bean.getMrpGatheringNo()) );
         
         }
         
      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : searchMrpGatheringList 종료");
      }
      return mrpGatheringList;

   }

   public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : openMrp 시작");
      }

      HashMap<String, Object> resultMap = new HashMap<>();

      try {
         String mpsNoList = mpsNoArr.toString().replace("[", "").replace("]", "");
            System.out.println("mpsNoList 값확인 : "+mpsNoList);
         resultMap = mrpDAO.openMrp(mpsNoList);

      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : openMrp 종료");
      }
      
      return resultMap;

   }

   public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<String> mpsList) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : registerMrp 시작");
      }

      HashMap<String, Object> resultMap = null;

      try {

         resultMap=mrpDAO.insertMrpList(mrpRegisterDate);

         // MPS 테이블에서 해당 mpsNo 의 MRP 적용상태를 "Y" 로 변경
         for (String mpsNo : mpsList) {

            mpsDAO.changeMrpApplyStatus(mpsNo, "Y");
            
         }
         
      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : registerMrp 종료");
      }
      return resultMap;
   }

   public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : batchMrpListProcess 시작");
      }

      HashMap<String, Object> resultMap = new HashMap<>();

      try {

         ArrayList<String> insertList = new ArrayList<>();
         ArrayList<String> updateList = new ArrayList<>();
         ArrayList<String> deleteList = new ArrayList<>();

         for (MrpTO bean : mrpTOList) {

            String status = bean.getStatus();

            switch (status) {

            case "INSERT":

               // dao 파트 시작
               mrpDAO.insertMrp(bean);
               // dao 파트 끝

               insertList.add(bean.getMrpNo());

               break;

            case "UPDATE":

               // dao 파트 시작
               mrpDAO.updateMrp(bean);
               // dao 파트 끝

               updateList.add(bean.getMrpNo());

               break;

            case "DELETE":

               // dao 파트 시작
               mrpDAO.deleteMrp(bean);
               // dao 파트 끝

               deleteList.add(bean.getMrpNo());

               break;

            }

         }

         resultMap.put("INSERT", insertList);
         resultMap.put("UPDATE", updateList);
         resultMap.put("DELETE", deleteList);

      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : batchMrpListProcess 종료");
      }

      return resultMap;
   }

   public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : getMrpGathering 시작");
      }

      ArrayList<MrpGatheringTO> mrpGatheringList = null;

      try {

         // mrp번호 배열 [mrp번호,mrp번호, ...] => "mrp번호,mrp번호, ..." 형식의 문자열로 변환
         String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
         System.out.println("mrpNoArr = "+mrpNoArr);
         System.out.println("mrpNoList = "+mrpNoList);
         mrpGatheringList = mrpGatheringDAO.getMrpGathering(mrpNoList);

      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : getMrpGathering 종료");
      }
      return mrpGatheringList;

   }
   public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,ArrayList<String> mrpNoArr,HashMap<String, String> mrpNoAndItemCodeMap) {
               //선택한날짜                                             getRowData                           MRP-NO : DK-AP01
      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : registerMrpGathering 시작");
      }

      HashMap<String, Object> resultMap = null;
      int seq=0;
      ArrayList<MrpGatheringTO> mrpGatheringList = null;
      try {
         // 소요량 취합일자로 새로운 소요량 취합번호 확인
         int i = mrpGatheringDAO.selectMrpGatheringCount(mrpGatheringRegisterDate); //선택한날짜
         /*
          * ( itemCode : 새로운 mrpGathering 일련번호 ) 키/값 Map => itemCode 로 mrpNo 와
          * mrpGatheringNo 를 매칭
          */
         HashMap<String, String> itemCodeAndMrpGatheringNoMap = new HashMap<>();

         // 새로운 mrpGathering 일련번호 양식 생성 : 등록일자 '2020-04-28' => 일련번호 'MG20200428-'
         StringBuffer newMrpGatheringNo = new StringBuffer();
         newMrpGatheringNo.append("MG"); 
         newMrpGatheringNo.append(mrpGatheringRegisterDate.replace("-", ""));
         newMrpGatheringNo.append("-");
         
         seq=mrpGatheringDAO.getMGSeqNo(); // 
         
         mrpGatheringList = getMrpGathering(mrpNoArr);
         // 새로운 mrpGathering 빈에 일련번호 입력 / status 를 "INSERT" 로 변경
         for (MrpGatheringTO bean : mrpGatheringList) { //newMrpGatheringList : 소요량 취합결과 그리드에 뿌려진 데이터값
        	 System.out.println("################################ : "+bean.getMrpGatheringNo());
            bean.setMrpGatheringNo(newMrpGatheringNo.toString() + String.format("%03d", i++));
            bean.setStatus("INSERT"); //bean 즉, MrpGatheringTO의 클래스주소에 소요량취합번호 + INSERT set
            bean.setMrpGatheringSeq(seq);
            // mrpGathering 빈의 itemCode 와 mrpGatheringNo 를 키값:밸류값으로 map 에 저장
            itemCodeAndMrpGatheringNoMap.put(bean.getItemCode(), bean.getMrpGatheringNo());
            
         }

         // 새로운 mrpGathering 빈을 batchListProcess 로 테이블에 저장, 결과 Map 반환
         resultMap = batchMrpGatheringListProcess(mrpGatheringList);//소요량 취합결과 그리드에 뿌려진 데이터값 //소요량취합번호, INSERT 추가됨
         
         // 생성된 mrp 일련번호를 저장할 TreeSet
         TreeSet<String> mrpGatheringNoSet = new TreeSet<>();

         // "INSERT_LIST" 목록에 "itemCode - mrpGatheringNo" 키/값 Map 이 있음
         @SuppressWarnings("unchecked")
         HashMap<String, String> mrpGatheringNoList = (HashMap<String, String>) resultMap.get("INSERT_MAP");//key(ItemCode):value(소요량취합번호)
         
         for (String mrpGatheringNo : mrpGatheringNoList.values()) {
            mrpGatheringNoSet.add(mrpGatheringNo); // mrpGatheringNoList 의 mrpGathering 일련번호들을 TreeSet 에 저장
            
         }

         resultMap.put("firstMrpGatheringNo", mrpGatheringNoSet.pollFirst()); // 최초 mrpGathering 일련번호를 결과 Map 에 저장
         resultMap.put("lastMrpGatheringNo", mrpGatheringNoSet.pollLast()); // 마지막 mrpGathering 일련번호를 결과 Map 에 저장

         
         // MRP 테이블에서 해당 mrpNo 의 mrpGatheringNo 저장, 소요량취합 적용상태를 "Y" 로 변경
         // itemCode 로 mrpNo 와 mrpGatheringNo 를 매칭시킨다
         for (String mrpNo : mrpNoAndItemCodeMap.keySet()) {
            String itemCode = mrpNoAndItemCodeMap.get(mrpNo);
            String mrpGatheringNo = itemCodeAndMrpGatheringNoMap.get(itemCode);
            mrpDAO.changeMrpGatheringStatus(mrpNo, mrpGatheringNo, "Y");
         }
         
         String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
         // MRP 적용상태를 "Y" 로 변경한 MRP 번호들을 결과 Map 에 저장
         resultMap.put("changeMrpGatheringStatus", mrpNoList);

         StringBuffer sb = new StringBuffer();
 		
 		//사진 캡쳐할려고 코드 분리 시켜놓음, mrpGatheringNoList.values()로 돌리는 첫번째 for문이랑 합쳐야함
 		for(String mrpGatheringNo : mrpGatheringNoList.values()) {
 			sb.append(mrpGatheringNo);
 			sb.append(",");
 		}
 		
 		sb.delete(sb.toString().length()-1, sb.toString().length());
 		
 		System.out.println("sb");
 		System.out.println(sb.toString());

 		HashMap<String, String> parameter = new HashMap<>();
 		parameter.put("mrpGatheringNoList", sb.toString());
 		mrpGatheringDAO.updateMrpGatheringContract(parameter);
         
         
      } catch (DataAccessException e) {
          logger.error(e.getMessage());
          throw e;
       }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : registerMrpGathering 종료");
      }
      return resultMap;
   }

   public HashMap<String, Object> batchMrpGatheringListProcess(ArrayList<MrpGatheringTO> mrpGatheringTOList) {

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : batchMrpGatheringListProcess 시작");
      }

      HashMap<String, Object> resultMap = new HashMap<>();

      try {

         HashMap<String, String> insertListMap = new HashMap<>(); // "itemCode : mrpGatheringNo" 의 맵
         ArrayList<String> insertList = new ArrayList<>();
         ArrayList<String> updateList = new ArrayList<>();
         ArrayList<String> deleteList = new ArrayList<>();

         for (MrpGatheringTO bean : mrpGatheringTOList) {//소요량 취합결과 그리드에 뿌려진 데이터값
            
            String status = bean.getStatus();
            
            switch (status) {

            case "INSERT":

               mrpGatheringDAO.insertMrpGathering(bean);
               
               insertList.add(bean.getMrpGatheringNo());
               //소요량취합번호 추가
               insertListMap.put(bean.getItemCode(), bean.getMrpGatheringNo());
               //map에 key(ItemCode) : value(getMrpGatheringNo)
               break;

            case "UPDATE":

               mrpGatheringDAO.updateMrpGathering(bean);

               updateList.add(bean.getMrpGatheringNo());

               break;

            case "DELETE":

               mrpGatheringDAO.deleteMrpGathering(bean);

               deleteList.add(bean.getMrpGatheringNo());

               break;

            }

         }

         resultMap.put("INSERT_MAP", insertListMap); //key(ItemCode) : value(getMrpGatheringNo)
         resultMap.put("INSERT", insertList); //소요량취합번호
         resultMap.put("UPDATE", updateList);
         resultMap.put("DELETE", deleteList);

      } catch (DataAccessException e) {
         logger.error(e.getMessage());
         throw e;
      }

      if (logger.isDebugEnabled()) {
         logger.debug("MrpApplicationServiceImpl : batchMrpGatheringListProcess 종료");
      }
      return resultMap;
   }

}