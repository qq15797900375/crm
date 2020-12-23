package com.myweb.crm.workbench.web.controller;

import com.myweb.crm.settings.domain.User;
import com.myweb.crm.settings.service.UserService;
import com.myweb.crm.settings.service.impl.UserServiceImpl;
import com.myweb.crm.utils.PrintJson;
import com.myweb.crm.utils.ServiceFactory;
import com.myweb.crm.workbench.service.ActivityService;
import com.myweb.crm.workbench.service.CustomerService;
import com.myweb.crm.workbench.service.impl.ActivityServiceImpl;
import com.myweb.crm.workbench.service.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TranController  extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入到客户控制器");

        String path = req.getServletPath();

        if ("/workbench/transaction/add.do".equals(path)){
            add(req,resp);
        }  else if ("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(req,resp);
        }  else {
            System.out.println("sb");
        }
    }

    private void getCustomerName(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("取得客户名称列表（根据客户名称进行模糊查询）");

        String name = req.getParameter("name");

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<String> stringList = cs.getCustomerName(name);

        PrintJson.printJsonObj(resp,stringList);

    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入到跳转到交易添加页的操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User>  userList = us.getUserList();

        req.setAttribute("userList",userList);
        req.getRequestDispatcher("/workbench/transaction/save.jsp").forward(req,resp);
    }

}
