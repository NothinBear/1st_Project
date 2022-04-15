package com.estimulo.system.authorityManager.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLogoutController extends MultiActionController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("*******IN*****MemberLogoutController*******IN*****");

        ModelAndView modelAndView = new ModelAndView("loginForm", null);
        HttpSession session = request.getSession();
        session.invalidate();
        logger.debug("*******OUT*****MemberLogoutController*******OUT*****");

        return modelAndView;
    }
}
