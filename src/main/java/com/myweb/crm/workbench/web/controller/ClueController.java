package com.myweb.crm.workbench.web.controller;

import com.myweb.crm.settings.domain.User;
import com.myweb.crm.settings.service.UserService;
import com.myweb.crm.settings.service.impl.UserServiceImpl;
import com.myweb.crm.utils.DateTimeUtil;
import com.myweb.crm.utils.PrintJson;
import com.myweb.crm.utils.ServiceFactory;
import com.myweb.crm.utils.UUIDUtil;
import com.myweb.crm.workbench.domain.Clue;
import com.myweb.crm.workbench.service.ClueService;
import com.myweb.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到线索控制器");

        String path = req.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(path)){
            getUserList(req,resp);
        } else if ("/workbench/clue/save.do".equals(path)){
            save(req,resp);
        } else if ("/workbench/clue/detail.do".equals(path)){
            detail(req,resp);
        } else {
            System.out.println("sb");
        }
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("跳转到线索的详细信息也");

        String id = req.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue clue = cs.detail(id);

        req.setAttribute("clue",clue);
        req.getRequestDispatcher("/workbench/clue/detail.jsp").forward(req,resp);

    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行线索的添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = req.getParameter("fullname");
        String appellation = req.getParameter("appellation");
        String owner = req.getParameter("owner");
        String company = req.getParameter("company");
        String job = req.getParameter("job");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String website  = req.getParameter("website");
        String mphone = req.getParameter("mphone");
        String state = req.getParameter("state");
        String source = req.getParameter("source");
        String description = req.getParameter("description");
        String contactSummary = req.getParameter("contactSummary");
        String nextContactTime = req.getParameter("nextContactTime");
        String address = req.getParameter("address");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)req.getSession().getAttribute("user")).getName();

        Clue clue = new Clue();

        clue.setAddress(address);
        clue.setAppellation(appellation);
        clue.setCompany(company);
        clue.setContactSummary(contactSummary);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setEmail(email);
        clue.setFullname(fullname);
        clue.setId(id);
        clue.setOwner(owner);
        clue.setJob(job);
        clue.setMphone(mphone);
        clue.setNextContactTime(nextContactTime);
        clue.setPhone(phone);
        clue.setSource(source);
        clue.setState(state);
        clue.setWebsite(website);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.save(clue);

        PrintJson.printJsonFlag(resp,flag);

    }


    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList = us.getUserList();
        PrintJson.printJsonObj(resp,userList);
    }


}
