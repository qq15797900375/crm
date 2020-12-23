package com.myweb.crm.workbench.service;

import com.myweb.crm.workbench.domain.Clue;
import com.myweb.crm.workbench.domain.Tran;

public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, String createBy, Tran t);
}
