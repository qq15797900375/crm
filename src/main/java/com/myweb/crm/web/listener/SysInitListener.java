package com.myweb.crm.web.listener;

import com.myweb.crm.settings.domain.DicValue;
import com.myweb.crm.settings.service.DicService;
import com.myweb.crm.settings.service.impl.DicServiceImpl;
import com.myweb.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.security.Key;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("服务器缓存处理数据字典开始");

        ServletContext application = sce.getServletContext();

        DicService ds= (DicService) ServiceFactory.getService(new DicServiceImpl());

        Map<String, List<DicValue>> map = ds.getAll();

        Set<String> set = map.keySet();

        for (String key : set) {
            application.setAttribute(key,map.get(key));
        }

        System.out.println("服务器缓存处理数据字典结束");

        //处理StageToPossibility.properties文件
        Map<String,String> StageToPossibilityMap = new HashMap<String, String>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("StageToPossibility");
        Enumeration<String> stringEnumeration = resourceBundle.getKeys();

        while (stringEnumeration.hasMoreElements()){

            String key = stringEnumeration.nextElement();
            String value = resourceBundle.getString(key);
            StageToPossibilityMap.put(key,value);

        }

        application.setAttribute("StageToPossibilityMap",StageToPossibilityMap);

    }
}
