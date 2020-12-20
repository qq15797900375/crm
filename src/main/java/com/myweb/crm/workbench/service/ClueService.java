package com.myweb.crm.workbench.service;

import com.myweb.crm.workbench.domain.Clue;

public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);
}
