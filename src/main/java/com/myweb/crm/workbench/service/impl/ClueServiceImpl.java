package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.workbench.dao.ClueActivityRelationDao;
import com.myweb.crm.workbench.dao.ClueDao;
import com.myweb.crm.workbench.domain.Clue;
import com.myweb.crm.workbench.service.ClueService;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    public boolean unbund(String id) {

        boolean flag = true;

        int count = clueActivityRelationDao.unbund(id);

        if (count != 1){

            flag = false;

        }
        return flag;
    }

    public Clue detail(String id) {

        Clue clue = clueDao.detail(id);

        return clue;
    }

    public boolean save(Clue clue) {
        boolean flag = true;

        int count = clueDao.save(clue);

        if (count != 1){

            flag = false;

        }

        return flag;
    }
}
