package com.myweb.settings.test;

import com.myweb.crm.utils.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {

    public static void main(String[] args) {
        String expireTime = "2029-10-10 10:10:10";

        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String str = sdf.format(date);
        System.out.println(str);
        System.out.println(DateTimeUtil.getSysTime());
        System.out.println(expireTime.compareTo(DateTimeUtil.getSysTime()));
    }
}
