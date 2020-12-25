package com.myweb.crm.workbench.web.controller;

import com.myweb.crm.settings.domain.User;
import com.myweb.crm.settings.service.UserService;
import com.myweb.crm.settings.service.impl.UserServiceImpl;
import com.myweb.crm.utils.DateTimeUtil;
import com.myweb.crm.utils.PrintJson;
import com.myweb.crm.utils.ServiceFactory;
import com.myweb.crm.utils.UUIDUtil;
import com.myweb.crm.workbench.domain.Tran;
import com.myweb.crm.workbench.domain.TranHistory;
import com.myweb.crm.workbench.service.ActivityService;
import com.myweb.crm.workbench.service.CustomerService;
import com.myweb.crm.workbench.service.TranService;
import com.myweb.crm.workbench.service.impl.ActivityServiceImpl;
import com.myweb.crm.workbench.service.impl.CustomerServiceImpl;
import com.myweb.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TranController  extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入到客户控制器");

        String path = req.getServletPath();

        if ("/workbench/transaction/add.do".equals(path)){
            add(req,resp);
        }  else if ("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(req,resp);
        }  else if ("/workbench/transaction/save.do".equals(path)){
            save(req,resp);
        }  else if ("/workbench/transaction/detail.do".equals(path)){
            detail(req,resp);
        }  else if ("/workbench/transaction/getTranHistoryListByTranId.do".equals(path)){
            getTranHistoryListByTranId(req,resp);
        }  else if ("/workbench/transaction/changeStage.do".equals(path)){
            changeStage(req,resp);
        }  else if ("/workbench/transaction/getCharts.do".equals(path)){
            getCharts(req,resp);
        }  else {
            System.out.println("sb");
        }
    }

    private void getCharts(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("取得交易阶段数量统计图表的数据");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        Map<String,Object> map = ts.getCharts();

        PrintJson.printJsonObj(resp,map);

    }

    private void changeStage(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("执行改变交易阶段的操作");

        String id = req.getParameter("id");
        String stage = req.getParameter("stage");
        String money = req.getParameter("money");
        String expectedDate = req.getParameter("expectedDate");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)req.getSession().getAttribute("user")).getName();

        Tran tran = new Tran();
        tran.setId(id);
        tran.setStage(stage);
        tran.setMoney(money);
        tran.setExpectedDate(expectedDate);
        tran.setEditTime(editTime);
        tran.setEditBy(editBy);
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        boolean flag = ts.changeStage(tran);

        Map<String,String> stageToPossibilityMap = (Map<String, String>) this.getServletContext().getAttribute("stageToPossibilityMap");
        tran.setPossibility(stageToPossibilityMap.get(stage));
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("success",flag);
        map.put("tran",tran);

        PrintJson.printJsonObj(resp,map);

    }

    private void getTranHistoryListByTranId(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("根据交易id取得相应的历史列表");

        String tranId = req.getParameter("tranId");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<TranHistory> historyList = ts.getTranHistoryListByTranId(tranId);
        Map<String,String> stageToPossibilityMap = (Map<String, String>) this.getServletContext().getAttribute("stageToPossibilityMap");

        for (TranHistory tranHistory: historyList) {

            String stage = tranHistory.getStage();
            tranHistory.setPossibility(stageToPossibilityMap.get(stage));

        }

        PrintJson.printJsonObj(resp,historyList);

    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("跳转到交易详细信息页");

        String id = req.getParameter("id");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        Tran tran = ts.detail(id);

        //处理可能性
        String stage = tran.getStage();
        System.out.println(stage);

        ServletContext application = this.getServletContext();
        //ServletContext application1 = req.getServletContext();
        //ServletContext application2 = this.getServletConfig().getServletContext();

        Map<String,String> stageToPossibilityMap = (Map<String, String>) application.getAttribute("stageToPossibilityMap");
        String possibility = stageToPossibilityMap.get(stage);
        tran.setPossibility(possibility);

        //req.setAttribute("possibility",possibility);
        req.setAttribute("tran",tran);
        req.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(req,resp);

    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("执行添加交易的操作");

        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String money = req.getParameter("money");
        String name = req.getParameter("name");
        String expectedDate = req.getParameter("expectedDate");
        String customerName = req.getParameter("customerName");
        String stage = req.getParameter("stage");
        String type = req.getParameter("type");
        String source = req.getParameter("source");
        String activityId = req.getParameter("activityId");
        String contactsId = req.getParameter("contactsId");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)req.getSession().getAttribute("user")).getName();
        String description = req.getParameter("description");
        String contactSummary = req.getParameter("contactSummary");
        String nextContactTime = req.getParameter("nextContactTime");

        Tran tran = new Tran();
        tran.setId(id);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setStage (stage);
        tran.setType(type);
        tran.setSource(source);
        tran.setActivityId(activityId);
        tran.setContactsId(contactsId);
        tran.setCreateTime(createTime);
        tran.setCreateBy(createBy);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        boolean flag = ts.save(tran,customerName);

        if (flag){

            resp.sendRedirect(req.getContextPath()+"/workbench/transaction/index.jsp");

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
