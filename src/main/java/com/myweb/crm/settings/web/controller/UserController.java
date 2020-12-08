package com.myweb.crm.settings.web.controller;

import com.myweb.crm.settings.domain.User;
import com.myweb.crm.settings.service.UserService;
import com.myweb.crm.settings.service.impl.UserServiceImpl;
import com.myweb.crm.utils.MD5Util;
import com.myweb.crm.utils.PrintJson;
import com.myweb.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到用户控制器");

        String path = req.getServletPath();

        if ("/settings/user/login.do".equals(path)){
            login(req,resp);
        } else {
            System.out.println("sb");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入登录验证操作");

        String loginAct = req.getParameter("loginAct");
        String loginPwd = req.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = req.getRemoteAddr();
        System.out.println("ip---------------"+ip);
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());

        try{
            System.out.println("sb");
            User user =  us.login(loginAct,loginPwd,ip);

            req.getSession().setAttribute("user",user);

            PrintJson.printJsonFlag(resp,true);
        } catch (Exception e){
            e.printStackTrace();

            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(resp,map);
        }
    }
}
