package com.xuecheng.content.service.impl;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UtilityClass  {

    /*@Autowired
    private Calculator calculator;*/
    public  String getCurrentTime( String a, String b) {
        CourseTest courseTest = new CourseTest();
        String add = courseTest.add(a , b);
        return add + ":name";

    }
    public  String getCurrentTime1( String a, String b) {
        String add = add(a , b);
        return add + ":name";

    }

    // 示例静态方法：计算两个整数的和


    // 示例静态方法：调用外部服务获取数据（模拟）
    public  String fetchDataFromExternalService() {
        long l = System.currentTimeMillis();
        return l + "";
    }
    public String add(String a, String b) {
        return a + b;
    }

    public String pubMethod() {
        String string = settString();
        return "hello "  + string;
    }

    private  String settString() {
        return "ll";
    }

    public static String staticMethod() {
        return "ll";
    }

    private  String privateMethod() {
        return "ll";
    }
}