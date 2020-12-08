package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.workbench.dao.ActivityDao;
import com.myweb.crm.workbench.domain.Activity;
import com.myweb.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    public boolean save(Activity activity) {

        boolean flag = true;

        int count = activityDao.save(activity);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}
