package com.myweb.crm.workbench.web.controller;

import com.myweb.crm.settings.domain.User;
import com.myweb.crm.settings.service.UserService;
import com.myweb.crm.settings.service.impl.UserServiceImpl;
import com.myweb.crm.utils.DateTimeUtil;
import com.myweb.crm.utils.PrintJson;
import com.myweb.crm.utils.ServiceFactory;
import com.myweb.crm.utils.UUIDUtil;
import com.myweb.crm.workbench.domain.Activity;
import com.myweb.crm.workbench.domain.Clue;
import com.myweb.crm.workbench.domain.Tran;
import com.myweb.crm.workbench.service.ActivityService;
import com.myweb.crm.workbench.service.ClueService;
import com.myweb.crm.workbench.service.impl.ActivityServiceImpl;
import com.myweb.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入到线索控制器");

        String path = req.getServletPath();

        System.out.println(path);

        if ("/workbench/clue/getUserList.do".equals(path)){
            getUserList(req,resp);
        } else if ("/workbench/clue/save.do".equals(path)){
            save(req,resp);
        } else if ("/workbench/clue/detail.do".equals(path)){
            detail(req,resp);
        } else if ("/workbench/clue/getActivityListByClueId.do".equals(path)){
            getActivityListByClueId(req,resp);
        } else if ("/workbench/clue/unbund.do".equals(path)){
            unbund(req,resp);
        } else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)){
            getActivityListByNameAndNotByClueId(req,resp);
        } else if ("/workbench/clue/bund.do".equals(path)){
            bund(req,resp);
        } else if ("/workbench/clue/getActivityListByName.do".equals(path)){
            getActivityListByName(req,resp);
        } else if ("/workbench/clue/convert.do".equals(path)){
            convert(req,resp);
        } else {
            System.out.println("sb");
        }
    }

    private void convert(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("执行线索转换操作");

        String clueId = req.getParameter("clueId");
        String flag = req.getParameter("flag");

        Tran t = null;
        String createBy = ((User)req.getSession().getAttribute("user")).getName();
        if ("true".equals(flag)){

            t = new Tran();

            String money = req.getParameter("money");
            String name = req.getParameter("name");
            String expectedDate = req.getParameter("expectedDate");
            String stage = req.getParameter("stage");
            String activityId = req.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            t.setActivityId(activityId);
            t.setMoney(money);
            t.setName(name);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
            t.setId(id);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);

        }

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag1 = cs.convert(clueId,createBy,t);

        if (flag1){

            resp.sendRedirect(req.getContextPath()+"/workbench/clue/index.jsp");

        }

    }

    private void getActivityListByName(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("查询市场活动列表");

        String aname = req.getParameter("aname");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> activityList = as.getActivityListByName(aname);

        PrintJson.printJsonObj(resp,activityList);

    }

    private void bund(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行关联市场活动操作");

        String cid = req.getParameter("cid");
        String[] aids = req.getParameterValues("aid");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.bund(cid,aids);

        PrintJson.printJsonFlag(resp,flag);

    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("查询市场活动列表（根据名称模糊查+排除掉已经关联指定线索的列表）");

        String aname = req.getParameter("aname");
        String clueId = req.getParameter("clueId");

        Map<String,String> map = new HashMap<String, String>();
        map.put("aname",aname);
        map.put("clueId",clueId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> activityList = as.getActivityListByNameAndNotByClueId(map);

        PrintJson.printJsonObj(resp,activityList);

    }

    private void unbund(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行解除关联操作");

        String id = req.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(resp,flag);
    }

    private void getActivityListByClueId(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("根据线索id查询关联的市场活动列表");

        String ClueId = req.getParameter("ClueId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> activityList = as.getActivityListByClueId(ClueId);

        PrintJson.printJsonObj(resp,activityList);

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
