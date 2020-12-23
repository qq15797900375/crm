package com.myweb.crm.workbench.service.impl;

import com.myweb.crm.utils.DateTimeUtil;
import com.myweb.crm.utils.SqlSessionUtil;
import com.myweb.crm.utils.UUIDUtil;
import com.myweb.crm.workbench.dao.*;
import com.myweb.crm.workbench.domain.*;
import com.myweb.crm.workbench.service.ClueService;

import java.util.List;

public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    public boolean convert(String clueId, String createBy, Tran t) {

        String createTime = DateTimeUtil.getSysTime();

        boolean flag = true;

        Clue clue = clueDao.getById(clueId);

        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);

        if (customer == null){

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setContactSummary(clue.getContactSummary());

            int count1 = customerDao.save(customer);

            if (count1 != 1){

                flag = false;

            }

        }

        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());

        int count2 = contactsDao.save(contacts);
        if (count2 != 1){
            flag = false;
        }

        List<ClueRemark> clueRemarkList = clueRemarkDao.getListByClueId(clueId);
        for (ClueRemark clueRemark : clueRemarkList) {

            String noteContent = clueRemark.getNoteContent();

            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(noteContent);
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3 != 1){
                flag = false;
            }

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(noteContent);
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4 != 1){
                flag = false;
            }

        }

        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {

            String activityId = clueActivityRelation.getActivityId();

            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());

            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5 != 1){
                flag = false;
            }

        }

        if (t != null) {

            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextContactTime(clue.getNextContactTime());
            t.setDescription(clue.getDescription());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(contacts.getId());
            t.setCustomerId(customer.getId());
            int count6 = tranDao.save(t);
            if (count6 != 1) {
                flag = false;
            }

            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());
            int count7 = tranHistoryDao.save(tranHistory);
            if (count7 != 1) {
                flag = false;
            }

        }
            for (ClueRemark clueRemark : clueRemarkList) {

                int count8 = clueRemarkDao.delete(clueRemark);
                if (count8 != 1) {
                    flag = false;
                }

            }

            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {

                int count9 = clueActivityRelationDao.delete(clueActivityRelation);
                if (count9 != 1){
                    flag = false;
                }

            }


        int count10 = clueDao.delete(clueId);
        if (count10 != 1){
            flag = false;
        }

        return flag;
    }

    public boolean bund(String cid, String[] aids) {

        boolean flag = true;

        for (String aid : aids) {

            ClueActivityRelation car = new ClueActivityRelation();

            car.setId(UUIDUtil.getUUID());
            car.setClueId(cid);
            car.setActivityId(aid);

            int count = clueActivityRelationDao.bund(car);

            if (count != 1){

                flag = false;

            }


        }

        return flag;
    }

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
