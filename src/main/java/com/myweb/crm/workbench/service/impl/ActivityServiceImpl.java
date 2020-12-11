package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.vo.PaginationVO;
import com.myweb.crm.workbench.dao.ActivityDao;
import com.myweb.crm.workbench.domain.Activity;
import com.myweb.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    public boolean save(Activity activity) {

        boolean flag = true;

        int count = activityDao.save(activity);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        int total = activityDao.getTotalByCondition(map);
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }


}
