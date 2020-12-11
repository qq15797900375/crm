package com.myweb.crm.workbench.service;

import com.myweb.crm.vo.PaginationVO;
import com.myweb.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);
}
