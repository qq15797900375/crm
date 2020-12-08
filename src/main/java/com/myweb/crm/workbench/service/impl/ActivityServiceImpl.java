package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.workbench.dao.ActivityDao;
import com.myweb.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}
