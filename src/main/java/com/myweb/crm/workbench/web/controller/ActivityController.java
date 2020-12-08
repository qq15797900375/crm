package com.myweb.crm.workbench.web.controller;

import com.myweb.crm.settings.domain.User;
import com.myweb.crm.settings.service.UserService;
import com.myweb.crm.settings.service.impl.UserServiceImpl;
import com.myweb.crm.utils.*;
import com.myweb.crm.workbench.domain.Activity;
import com.myweb.crm.workbench.service.ActivityService;
import com.myweb.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到市场活动控制器");

        String path = req.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(req,resp);
        } else if ("/workbench/activity/save.do".equals(path)){
            save(req,resp);
        } else {
            System.out.println("sb");
        }
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行市场活动的添加操作");

        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description  = req.getParameter("description");
        //创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人，当前登录用户
        String createBy = ((User)req.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();

        activity.setId(id);
        activity.setCost(cost);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(activity);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = us.getUserList();
        PrintJson.printJsonObj(resp,userList);
    }


}
