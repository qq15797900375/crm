package com.myweb.crm.settings.service.impl;

import com.myweb.crm.settings.dao.DicTypeDao;
import com.myweb.crm.settings.dao.DicValueDao;
import com.myweb.crm.settings.domain.DicType;
import com.myweb.crm.settings.domain.DicValue;
import com.myweb.crm.settings.service.DicService;
import com.myweb.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();

        List<DicType> dicTypeList = dicTypeDao.getTypeList();

        for (DicType dt : dicTypeList) {

            String code = dt.getCode();

            List<DicValue> dicValueList = dicValueDao.getListByCode(code);
            map.put(code+"List",dicValueList);

        }

        return map;
    }
}
