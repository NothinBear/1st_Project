package com.estimulo.system.authorityManager.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.estimulo.system.common.servlet.ModelAndView;
import com.estimulo.system.common.servlet.controller.MultiActionController;

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
