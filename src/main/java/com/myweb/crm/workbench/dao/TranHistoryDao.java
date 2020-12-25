package com.myweb.crm.workbench.dao;

import com.myweb.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getTranHistoryListByTranId(String tranId);
}
