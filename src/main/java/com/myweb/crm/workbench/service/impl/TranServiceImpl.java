package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.utils.DateTimeUtil;
import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.utils.UUIDUtil;
import com.myweb.crm.workbench.dao.CustomerDao;
import com.myweb.crm.workbench.dao.TranDao;
import com.myweb.crm.workbench.dao.TranHistoryDao;
import com.myweb.crm.workbench.domain.Customer;
import com.myweb.crm.workbench.domain.Tran;
import com.myweb.crm.workbench.domain.TranHistory;
import com.myweb.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public Map<String, Object> getCharts() {

        int total = tranDao.getTotal();

        List<Map<String,Object>> dataList = tranDao.getCharts();

        Map<String,Object> map = new HashMap<String, Object>();

        map.put("total",total);
        map.put("dataList",dataList);

        return map;
    }

    public boolean changeStage(Tran tran) {

        boolean flag = true;

        int count1 = tranDao.changeStage(tran);
        if (count1 != 1){

            flag = false;

        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(tran.getEditBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setTranId(tran.getId());

        int count2 = tranHistoryDao.save(tranHistory);
        if (count2 != 1){

            flag = false;

        }

        return flag;
    }

    public List<TranHistory> getTranHistoryListByTranId(String tranId) {

        List<TranHistory> historyList = tranHistoryDao.getTranHistoryListByTranId(tranId);

        return historyList;
    }

    public Tran detail(String id) {

        Tran tran = tranDao.detail(id);

        return tran;
    }

    public boolean save(Tran tran, String customerName) {

        boolean flag = true;

        Customer customer = customerDao.getCustomerByName(customerName);

        if (customer == null){

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());

            int count1 = customerDao.save(customer);
            if (count1 != 1){
                flag =false;
            }

        }

        tran.setCustomerId(customer.getId());

        int count2 = tranDao.save(tran);
        if (count2 != 1){
            flag =false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setCreateBy(tran.getCreateBy());

        int count3 = tranHistoryDao.save(tranHistory);
        if (count3 != 1){
            flag =false;
        }

        return flag;
    }
}
