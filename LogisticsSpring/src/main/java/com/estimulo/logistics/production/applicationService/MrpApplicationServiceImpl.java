package com.estimulo.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.estimulo.logistics.production.dao.MpsDAO;
import com.estimulo.logistics.production.dao.MrpDAO;
import com.estimulo.logistics.production.dao.MrpGatheringDAO;
import com.estimulo.logistics.production.to.MrpGatheringTO;
import com.estimulo.logistics.production.to.MrpTO;

public class MrpApplicationServiceImpl implements MrpApplicationService {

	// DAO 李몄“蹂��닔 �꽑�뼵
	private MpsDAO mpsDAO;
	private MrpDAO mrpDAO;
	private MrpGatheringDAO mrpGatheringDAO;

	public void setMpsDAO(MpsDAO mpsDAO) {
		this.mpsDAO = mpsDAO;
	}

	public void setMrpDAO(MrpDAO mrpDAO) {
		this.mrpDAO = mrpDAO;
	}

	public void setMrpGatheringDAO(MrpGatheringDAO mrpGatheringDAO) {
		this.mrpGatheringDAO = mrpGatheringDAO;
	}

	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {

		ArrayList<MrpTO> mrpList = null;
		
			mrpList = mrpDAO.selectMrpList(mrpGatheringStatusCondition);

		return mrpList;
	}

	public ArrayList<MrpTO> searchMrpListAsDate(String dateSearchCondtion, String startDate, String endDate) {

		ArrayList<MrpTO> mrpList = null;

			mrpList = mrpDAO.selectMrpListAsDate(dateSearchCondtion, startDate, endDate);

		return mrpList;

	}

	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {

		ArrayList<MrpTO> mrpList = null;

			mrpList = mrpDAO.selectMrpListAsMrpGatheringNo(mrpGatheringNo);

		return mrpList;

	}

	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {

		ArrayList<MrpGatheringTO> mrpGatheringList = null;

			mrpGatheringList = mrpGatheringDAO.selectMrpGatheringList(dateSearchCondtion, startDate, endDate);

			for (MrpGatheringTO bean : mrpGatheringList) {

				bean.setMrpTOList(mrpDAO.selectMrpListAsMrpGatheringNo(bean.getMrpGatheringNo()));

			}

		return mrpGatheringList;

	}

	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {

		HashMap<String, Object> resultMap = new HashMap<>();

			String mpsNoList = mpsNoArr.toString().replace("[", "").replace("]", "");
			resultMap = mrpDAO.openMrp(mpsNoList);

		return resultMap;

	}

	public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<String> mpsList) {

		HashMap<String, Object> resultMap = null;

			resultMap = mrpDAO.insertMrpList(mrpRegisterDate);

			// MPS �뀒�씠釉붿뿉�꽌 �빐�떦 mpsNo �쓽 MRP �쟻�슜�긽�깭瑜� "Y" 濡� 蹂�寃�
			for (String mpsNo : mpsList) {

				mpsDAO.changeMrpApplyStatus(mpsNo, "Y");

			}

		return resultMap;
	}

	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (MrpTO bean : mrpTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					// dao �뙆�듃 �떆�옉
					mrpDAO.insertMrp(bean);
					// dao �뙆�듃 �걹

					insertList.add(bean.getMrpNo());

					break;

				case "UPDATE":

					// dao �뙆�듃 �떆�옉
					mrpDAO.updateMrp(bean);
					// dao �뙆�듃 �걹

					updateList.add(bean.getMrpNo());

					break;

				case "DELETE":

					// dao �뙆�듃 �떆�옉
					mrpDAO.deleteMrp(bean);
					// dao �뙆�듃 �걹

					deleteList.add(bean.getMrpNo());

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		return resultMap;
	}

	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {

		ArrayList<MrpGatheringTO> mrpGatheringList = null;

			// mrp踰덊샇 諛곗뿴 [mrp踰덊샇,mrp踰덊샇, ...] => "mrp踰덊샇,mrp踰덊샇, ..." �삎�떇�쓽 臾몄옄�뿴濡� 蹂��솚
			String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
			mrpGatheringList = mrpGatheringDAO.getMrpGathering(mrpNoList);

		return mrpGatheringList;

	}

	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate, ArrayList<String> mrpNoArr,
			HashMap<String, String> mrpNoAndItemCodeMap) {
		// �꽑�깮�븳�궇吏� getRowData MRP-NO : DK-AP01

		HashMap<String, Object> resultMap = null;
		int seq = 0;
		ArrayList<MrpGatheringTO> mrpGatheringList = null;
		
			// �냼�슂�웾 痍⑦빀�씪�옄濡� �깉濡쒖슫 �냼�슂�웾 痍⑦빀踰덊샇 �솗�씤
			int i = mrpGatheringDAO.selectMrpGatheringCount(mrpGatheringRegisterDate); // �꽑�깮�븳�궇吏�
			/*
			 * ( itemCode : �깉濡쒖슫 mrpGathering �씪�젴踰덊샇 ) �궎/媛� Map => itemCode 濡� mrpNo ��
			 * mrpGatheringNo 瑜� 留ㅼ묶
			 */
			HashMap<String, String> itemCodeAndMrpGatheringNoMap = new HashMap<>();

			// �깉濡쒖슫 mrpGathering �씪�젴踰덊샇 �뼇�떇 �깮�꽦 : �벑濡앹씪�옄 '2020-04-28' => �씪�젴踰덊샇 'MG20200428-'
			StringBuffer newMrpGatheringNo = new StringBuffer();
			newMrpGatheringNo.append("MG");
			newMrpGatheringNo.append(mrpGatheringRegisterDate.replace("-", ""));
			newMrpGatheringNo.append("-");

			seq = mrpGatheringDAO.getMGSeqNo(); //

			mrpGatheringList = getMrpGathering(mrpNoArr);
			// �깉濡쒖슫 mrpGathering 鍮덉뿉 �씪�젴踰덊샇 �엯�젰 / status 瑜� "INSERT" 濡� 蹂�寃�
			for (MrpGatheringTO bean : mrpGatheringList) { // newMrpGatheringList : �냼�슂�웾 痍⑦빀寃곌낵 洹몃━�뱶�뿉 肉뚮젮吏� �뜲�씠�꽣媛�
				bean.setMrpGatheringNo(newMrpGatheringNo.toString() + String.format("%03d", i++));
				bean.setStatus("INSERT"); // bean 利�, MrpGatheringTO�쓽 �겢�옒�뒪二쇱냼�뿉 �냼�슂�웾痍⑦빀踰덊샇 + INSERT set
				bean.setMrpGatheringSeq(seq);
				// mrpGathering 鍮덉쓽 itemCode �� mrpGatheringNo 瑜� �궎媛�:諛몃쪟媛믪쑝濡� map �뿉 ���옣
				itemCodeAndMrpGatheringNoMap.put(bean.getItemCode(), bean.getMrpGatheringNo());

			}

			// �깉濡쒖슫 mrpGathering 鍮덉쓣 batchListProcess 濡� �뀒�씠釉붿뿉 ���옣, 寃곌낵 Map 諛섑솚
			resultMap = batchMrpGatheringListProcess(mrpGatheringList);// �냼�슂�웾 痍⑦빀寃곌낵 洹몃━�뱶�뿉 肉뚮젮吏� �뜲�씠�꽣媛� //�냼�슂�웾痍⑦빀踰덊샇, INSERT 異붽��맖

			// �깮�꽦�맂 mrp �씪�젴踰덊샇瑜� ���옣�븷 TreeSet
			TreeSet<String> mrpGatheringNoSet = new TreeSet<>();

			// "INSERT_LIST" 紐⑸줉�뿉 "itemCode - mrpGatheringNo" �궎/媛� Map �씠 �엳�쓬
			@SuppressWarnings("unchecked")
			HashMap<String, String> mrpGatheringNoList = (HashMap<String, String>) resultMap.get("INSERT_MAP");// key(ItemCode):value(�냼�슂�웾痍⑦빀踰덊샇)

			for (String mrpGatheringNo : mrpGatheringNoList.values()) {
				mrpGatheringNoSet.add(mrpGatheringNo); // mrpGatheringNoList �쓽 mrpGathering �씪�젴踰덊샇�뱾�쓣 TreeSet �뿉 ���옣

			}

			resultMap.put("firstMrpGatheringNo", mrpGatheringNoSet.pollFirst()); // 理쒖큹 mrpGathering �씪�젴踰덊샇瑜� 寃곌낵 Map �뿉 ���옣
			resultMap.put("lastMrpGatheringNo", mrpGatheringNoSet.pollLast()); // 留덉�留� mrpGathering �씪�젴踰덊샇瑜� 寃곌낵 Map �뿉 ���옣

			// MRP �뀒�씠釉붿뿉�꽌 �빐�떦 mrpNo �쓽 mrpGatheringNo ���옣, �냼�슂�웾痍⑦빀 �쟻�슜�긽�깭瑜� "Y" 濡� 蹂�寃�
			// itemCode 濡� mrpNo �� mrpGatheringNo 瑜� 留ㅼ묶�떆�궓�떎
			for (String mrpNo : mrpNoAndItemCodeMap.keySet()) {
				String itemCode = mrpNoAndItemCodeMap.get(mrpNo);
				String mrpGatheringNo = itemCodeAndMrpGatheringNoMap.get(itemCode);
				mrpDAO.changeMrpGatheringStatus(mrpNo, mrpGatheringNo, "Y");
			}

			String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
			// MRP �쟻�슜�긽�깭瑜� "Y" 濡� 蹂�寃쏀븳 MRP 踰덊샇�뱾�쓣 寃곌낵 Map �뿉 ���옣
			resultMap.put("changeMrpGatheringStatus", mrpNoList);

			StringBuffer sb = new StringBuffer();

			// �궗吏� 罹≪퀜�븷�젮怨� 肄붾뱶 遺꾨━ �떆耳쒕넃�쓬, mrpGatheringNoList.values()濡� �룎由щ뒗 泥ル쾲吏� for臾몄씠�옉 �빀爾먯빞�븿
			for (String mrpGatheringNo : mrpGatheringNoList.values()) {
				sb.append(mrpGatheringNo);
				sb.append(",");
			}

			sb.delete(sb.toString().length() - 1, sb.toString().length());


			HashMap<String, String> parameter = new HashMap<>();
			parameter.put("mrpGatheringNoList", sb.toString());
			mrpGatheringDAO.updateMrpGatheringContract(parameter);

		return resultMap;
	}

	public HashMap<String, Object> batchMrpGatheringListProcess(ArrayList<MrpGatheringTO> mrpGatheringTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			HashMap<String, String> insertListMap = new HashMap<>(); // "itemCode : mrpGatheringNo" �쓽 留�
			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (MrpGatheringTO bean : mrpGatheringTOList) {// �냼�슂�웾 痍⑦빀寃곌낵 洹몃━�뱶�뿉 肉뚮젮吏� �뜲�씠�꽣媛�

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					mrpGatheringDAO.insertMrpGathering(bean);

					insertList.add(bean.getMrpGatheringNo());
					// �냼�슂�웾痍⑦빀踰덊샇 異붽�
					insertListMap.put(bean.getItemCode(), bean.getMrpGatheringNo());
					// map�뿉 key(ItemCode) : value(getMrpGatheringNo)
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

			resultMap.put("INSERT_MAP", insertListMap); // key(ItemCode) : value(getMrpGatheringNo)
			resultMap.put("INSERT", insertList); // �냼�슂�웾痍⑦빀踰덊샇
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		return resultMap;
	}

}