package com.myweb.crm.settings.service;

import com.myweb.crm.exception.LoginException;
import com.myweb.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
