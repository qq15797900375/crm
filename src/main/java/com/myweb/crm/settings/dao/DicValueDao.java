package com.myweb.crm.settings.dao;

import com.myweb.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getListByCode(String code);

}
