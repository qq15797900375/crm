package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.settings.dao.UserDao;
import com.myweb.crm.settings.domain.User;
import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.vo.PaginationVO;
import com.myweb.crm.workbench.dao.ActivityDao;
import com.myweb.crm.workbench.dao.ActivityRemarkDao;
import com.myweb.crm.workbench.domain.Activity;
import com.myweb.crm.workbench.domain.ActivityRemark;
import com.myweb.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public List<Activity> getActivityListByName(String aname) {

        List<Activity> activityList = activityDao.getActivityListByName(aname);
        return activityList;
    }

    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {

        List<Activity> activityList = activityDao.getActivityListByNameAndNotByClueId(map);

        return activityList;
    }

    public List<Activity> getActivityListByClueId(String clueId) {

        List<Activity> activityList = activityDao.getActivityListByClueId(clueId);

        return activityList;
    }

    public boolean updateRemark(ActivityRemark ar) {

        boolean flag = true;

        int count = activityRemarkDao.updateRemark(ar);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public boolean saveRemark(ActivityRemark ar) {

        boolean flag = true;

        int count = activityRemarkDao.saveRemark(ar);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public boolean deleteRemark(String id) {

        boolean flag = true;

        int count = activityRemarkDao.deleteById(id);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByAid(activityId);

        return arList;
    }

    public Activity detail(String id) {

        Activity activity = activityDao.detail(id);

        return activity;
    }

    public boolean update(Activity activity) {

        boolean flag = true;

        int count = activityDao.update(activity);

        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public Map<String, Object> getUserListAndActivity(String id) {

        //获取uList
        List<User> uList = userDao.getUserList();
        //获取a
        Activity a = activityDao.getById(id);
        //将uList和a打包成map返回
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("uList",uList);
        map.put("a",a);

        return  map;
    }

    public boolean delete(String[] ids) {

        boolean flag = true;
        //查询需要删除的备注的数量
        int count1 = activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数（实际删除的数量）
        int count2 = activityRemarkDao.deleteByAids(ids);

        if (count1 != count2){
            flag = false;
        }
        //删除市场活动

        int count3 = activityDao.delete(ids);

        if (count3 != ids.length){
            flag = false;
        }

        return  flag;
    }

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
