package com.estimulo.system.authorityManager.controller;

import java.io.PrintWriter;
import java.util.HashMap;

// import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estimulo.hr.affair.to.EmpInfoTO;
import com.estimulo.system.authorityManager.exception.IdNotFoundException;
import com.estimulo.system.authorityManager.exception.PwMissMatchException;
import com.estimulo.system.authorityManager.exception.PwNotFoundException;
import com.estimulo.system.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.estimulo.system.authorityManager.serviceFacade.AuthorityManagerServiceFacadeImpl;
import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemberLogInController extends MultiActionController {
    private static Logger logger = LoggerFactory.getLogger(MemberLogInController.class);
    private static AuthorityManagerServiceFacade managerSF = AuthorityManagerServiceFacadeImpl.getInstance();
    private static Gson gson = new GsonBuilder().serializeNulls().create();

    public ModelAndView LogInCheck(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("********IN****** MemberLogInController :  LogInCheck ********IN******");
      //  String viewName = null;
        HashMap<String, Object> model = new HashMap<>();
        PrintWriter out = null;
        try {
            out = response.getWriter();

            HttpSession session = request.getSession();
            String companyCode = request.getParameter("companyCode");      //COM-01
            String workplaceCode = request.getParameter("workplaceCode");  //BRC-01
            String userId = request.getParameter("userId");               //1111
            String userPassword = request.getParameter("userPassword");   //1111

            EmpInfoTO TO = managerSF.accessToAuthority(companyCode, workplaceCode, userId, userPassword);
            if (TO != null) {       
              // ServletContext application = request.getServletContext();	      // 로그인 성공 했을 경우 그사람의 정보를 클라이언트가 종료되기전 까지 저장 해준다
                session.setAttribute("sessionID", session.getId());
                session.setAttribute("userId", TO.getUserId());                //유저ID
                session.setAttribute("empCode", TO.getEmpCode());              //사원번호
                session.setAttribute("empName", TO.getEmpName());              //사원이름
                session.setAttribute("deptCode", TO.getDeptCode());            //부서코드
                session.setAttribute("deptName", TO.getDeptName());            //부서명
                session.setAttribute("positionCode", TO.getPositionCode());    //직급코드
                session.setAttribute("positionName", TO.getPositionName());    //직급명
                session.setAttribute("companyCode", TO.getCompanyCode());      //회사코드
                session.setAttribute("workplaceCode", workplaceCode);          //사업장코드
                session.setAttribute("workplaceName", TO.getWorkplaceName());  //사업장명
                session.setAttribute("image", TO.getImage()); 				   // 사진 
                session.setAttribute("authorityGroupCode", TO.getUserAuthorityGroupList()); //권한그룹리스트       
                session.setAttribute("authorityGroupMenuList", TO.getUserAuthorityGroupMenuList());  //권한그룹메뉴리스트
                
                // 메뉴 DB에서 가져오기
                String[] allMenuList = managerSF.getAllMenuList();     

                session.setAttribute("allMenuList", allMenuList[0]);   // 모든 메뉴 리스트
                session.setAttribute("navMenuList", allMenuList[1]);   // nav바 메뉴 리스트

                logger.debug("로그인성공");   
            }

        } catch (IdNotFoundException e1) {   
            e1.printStackTrace();
            model.put("errorCode", -2);
            model.put("errorMsg", e1.getMessage());
        } catch (PwNotFoundException e2) {
            e2.printStackTrace();
            model.put("errorCode", -3);
            model.put("errorMsg", e2.getMessage());
        } catch (PwMissMatchException e3) {
            e3.printStackTrace();
            model.put("errorCode", -4);
            model.put("errorMsg", e3.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            model.put("errorCode", -5);
            model.put("errorMsg", e.getMessage());
        } finally {
            out.println(gson.toJson(model));
            out.close();
        }

        // ModelAndView modelAndView = new ModelAndView(viewName, model);
        logger.debug("********OUT****** MemberLogInController :  LogInCheck ********OUT******");
        return null;
    }
}
