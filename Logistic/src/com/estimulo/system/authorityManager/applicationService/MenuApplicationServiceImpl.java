package com.estimulo.system.authorityManager.applicationService;

import java.util.ArrayList;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.system.authorityManager.dao.MenuAuthorityDAO;
import com.estimulo.system.authorityManager.dao.MenuAuthorityDAOImpl;
import com.estimulo.system.authorityManager.dao.MenuDAO;
import com.estimulo.system.authorityManager.dao.MenuDAOImpl;
import com.estimulo.system.authorityManager.to.MenuAuthorityTO;
import com.estimulo.system.authorityManager.to.MenuTO;
import com.estimulo.system.common.exception.DataAccessException;

public class MenuApplicationServiceImpl implements MenuApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(MenuApplicationServiceImpl.class);

	// 싱글톤
	private static MenuApplicationService instance = new MenuApplicationServiceImpl();

	private MenuApplicationServiceImpl() {
	}

	public static MenuApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ MenuApplicationServiceImpl 객체접근");
		}
		return instance;
	}

	// DAO 참조변수 선언
	private static MenuAuthorityDAO menuAuthorityDAO = MenuAuthorityDAOImpl.getInstance();
	// private static UserMenuDAO userMenuDAO = UserMenuDAOImpl.getInstance();
	private static MenuDAO menuDAO = MenuDAOImpl.getInstance();

	@Override
	public void insertMenuAuthority(String authorityGroupCode, ArrayList<MenuAuthorityTO> menuAuthorityTOList) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("MenuApplicationServiceImpl : insertMenuAuthority 시작");
		}

		try {
			// 해당 권한그룹의 기존 메뉴권한 정보 삭제
			menuAuthorityDAO.deleteMenuAuthority(authorityGroupCode);

			// 새로운 메뉴권한 정보 insert
			for (MenuAuthorityTO bean : menuAuthorityTOList) {

				menuAuthorityDAO.insertMenuAuthority(bean);

			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MenuApplicationServiceImpl : insertMenuAuthority 종료");
		}
	}

	@Override
	public ArrayList<MenuAuthorityTO> getMenuAuthority(String authorityGroupCode) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("MenuApplicationServiceImpl : getMenuAuthority 시작");
		}

		ArrayList<MenuAuthorityTO> menuAuthorityTOList = null;

		try {
			menuAuthorityTOList = menuAuthorityDAO.selectMenuAuthorityList(authorityGroupCode);
			 
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MenuApplicationServiceImpl : getMenuAuthority 종료");
		}

		return menuAuthorityTOList;
	}

	public String[] getAllMenuList() {

		if (logger.isDebugEnabled()) {
			logger.debug("MenuApplicationServiceImpl : getAllMenuList 시작");
		}

		// 메뉴와 nav메뉴를 담을 변수
		StringBuffer menuList = new StringBuffer();
		StringBuffer navMenuList = new StringBuffer();
		String[] menuArr = new String[2];
		// nav메뉴 정렬을 위한 treemap
		TreeMap<Integer, MenuTO> treeMap = new TreeMap<>();

		try {
			ArrayList<MenuTO> allMenuTOList = menuDAO.selectAllMenuList();

			ArrayList<MenuTO> lv0 = new ArrayList<>();
			ArrayList<MenuTO> lv1 = new ArrayList<>();
			ArrayList<MenuTO> lv2 = new ArrayList<>();

			// 메뉴 레벨별로 나누어 ArrayList에 담기
			for (MenuTO bean : allMenuTOList) {
				if (bean.getMenuURL() != null) {
					String lv = bean.getMenuLevel();
					switch (lv) {
					case "0":
						lv0.add(bean); break;
					case "1":
						lv1.add(bean); break;
					default:
						lv2.add(bean);
					}
				}
			}

			menuList.append("<ul class='list-unstyled components mb-5' id='menuUlTag'>");

			// 레벨0 메뉴
			for (MenuTO bean0 : lv0) {

				menuList.append("<li>");
				menuList.append("<a href=" + bean0.getMenuURL()
						+ " data-toggle='collapse' aria-expanded='false' class='dropdown-toggle'>");
				menuList.append(bean0.getMenuName() + "</a>");
				menuList.append("<ul class='collapse list-unstyled' id=" + bean0.getMenuURL().substring(1) + ">");

				// 레벨1 메뉴
				for (MenuTO bean1 : lv1) {

					menuList.append("<li>");

					// 자식이 없는 레벨1 메뉴
					if (bean1.getChildMenu() == null && bean1.getParentMenuCode().equals(bean0.getMenuCode())) {

						menuList.append("<a href=" + bean1.getMenuURL() + " id=" + bean1.getMenuCode() + " class='m'>"
								+ bean1.getMenuName() + "</a>");

						if (bean1.getNavMenu() != null) {
							treeMap.put(Integer.parseInt(bean1.getNavMenu()), bean1);
						}

						// 자식이 있는 레벨1 메뉴
					} else if (bean1.getChildMenu() != null && bean1.getParentMenuCode().equals(bean0.getMenuCode())) {

						menuList.append("<a href=" + bean1.getMenuURL()
								+ " data-toggle='collapse' aria-expanded='false' class='dropdown-toggle' ");
						menuList.append("id=" + bean1.getMenuCode() + ">" + bean1.getMenuName() + "</a>");
						menuList.append(
								"<ul class='collapse list-unstyled' id=" + bean1.getMenuURL().substring(1) + ">");

						// 레벨2 메뉴
						for (MenuTO bean2 : lv2) {

							if (bean2.getParentMenuCode().equals(bean1.getMenuCode())) {
								menuList.append("<li>");
								menuList.append("<a href=" + bean2.getMenuURL() + " id=" + bean2.getMenuCode()
										+ " class='m'>" + bean2.getMenuName() + "</a>");
								menuList.append("</li>");
							}

							if (bean2.getNavMenu() != null) {
								treeMap.put(Integer.parseInt(bean2.getNavMenu()), bean2);
							}
						}
						menuList.append("</ul>");
					}
					menuList.append("</li>");
				}
				menuList.append("</ul>");
				menuList.append("</li>");
			}
			menuList.append("</ul>");

			// nav메뉴
			navMenuList.append("<ul class='nav navbar-nav ml-auto'>");
			for (Integer i : treeMap.keySet()) {
				MenuTO bean = treeMap.get(i);
				navMenuList.append("<li class='nav-item'>");
				navMenuList
						.append("<a class='nav-link m' href=" + bean.getMenuURL() + " id=" + bean.getMenuCode() + ">");
				navMenuList.append(bean.getNavMenuName() + "</a>");
				navMenuList.append("</li>");
			}
			navMenuList.append("</ul>");

			menuArr[0] = menuList.toString();
			menuArr[1] = navMenuList.toString();

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MenuApplicationServiceImpl : getAllMenuList 종료");
		}

		return menuArr;
	}

}
