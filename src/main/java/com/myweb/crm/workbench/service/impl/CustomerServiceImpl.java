package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.workbench.dao.CustomerDao;
import com.myweb.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public List<String> getCustomerName(String name) {

        List<String> stringList = customerDao.getCustomerName(name);

        return stringList;
    }
}
