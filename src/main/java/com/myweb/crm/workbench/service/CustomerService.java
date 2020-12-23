package com.myweb.crm.workbench.service;

import com.myweb.crm.workbench.dao.CustomerDao;

import java.util.List;

public interface CustomerService {


    List<String> getCustomerName(String name);
}
