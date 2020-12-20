package com.myweb.crm.web.listener;

import com.myweb.crm.settings.domain.DicValue;
import com.myweb.crm.settings.service.DicService;
import com.myweb.crm.settings.service.impl.DicServiceImpl;
import com.myweb.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    }
}
