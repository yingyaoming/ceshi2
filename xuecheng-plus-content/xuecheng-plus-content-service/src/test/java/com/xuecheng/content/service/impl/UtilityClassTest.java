package com.xuecheng.content.service.impl;


import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.aop.config.PointcutEntry;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UtilityClass.class,System.class,CourseTest.class})
public class UtilityClassTest {
    @InjectMocks
    private UtilityClass utilityClass;

    private UtilityClass spy;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(UtilityClass.class);
        spy = PowerMockito.spy(new UtilityClass());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCurrentTime() throws Exception {
        //UtilityClass spy = PowerMockito.spy(new UtilityClass());
        //PowerMockito.doReturn("liwei").when(spy).add(any(),any());
        PowerMockito.when(spy.add(any(),any())).thenReturn("liwei");
        String currentTime = spy.getCurrentTime1("q", "e");
        System.out.println(currentTime);

        /*CourseTest mock = PowerMockito.mock(CourseTest.class);
        PowerMockito.when(mock.add(anyString(),anyString())).thenReturn("你好");
        String result = utilityClass.getCurrentTime1("l", "l");
        System.out.println(result);*/

        /*CourseTest mock = PowerMockito.mock(CourseTest.class);
        PowerMockito.whenNew(CourseTest.class).withNoArguments().thenReturn(mock);
        PowerMockito.when(mock.add(anyString(),anyString())).thenReturn("你好");
        String result = utilityClass.getCurrentTime("l", "l");
        System.out.println(result);*/
    }

    @Test
    public void getCurrentTime1() throws Exception {

    }

    @Test
    public void add() {

        utilityClass.add("2", "3");
    }

    @Test
    public void fetchDataFromExternalService() {
        //模拟system的静态方法
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(12L);

        String act = utilityClass.fetchDataFromExternalService();
        assertEquals("12", act);
    }

    @Test
    public void publicMethod() throws Exception {
        // Arrange (准备数据)
        //UtilityClass utilityClass = spy(new UtilityClass());
        // 使用 doReturn().when() 模拟私有方法的行为
        doReturn("成功了").when(spy, "settString");
        PowerMockito.when(spy.add(any(),any())).thenReturn("liwei");
        String actual = spy.pubMethod();
        System.out.println(actual);
        // Act (调用待测试的方法)
        //String actual = utilityClass.publicMethod();
        //System.out.println(actual);
        // Assert (验证结果)
        //ssertEquals("成功了", actual);

        // 验证私有方法是否被调用
        //verifyPrivate(utilityClass).invoke("privateMethod");

    }

    @Test
    public void privateMethod() throws Exception{
        UtilityClass spy = PowerMockito.spy(new UtilityClass());
        PowerMockito.doReturn("world").when(spy, "settString");
        System.out.println(spy.pubMethod());

    }

    @Test
    public void  staticMethod() {

        PowerMockito.when(UtilityClass.staticMethod()).thenReturn("哈哈");
        String s = UtilityClass.staticMethod();
        System.out.println(s);
    }

    @Test
    public void  staticMethod2() {

        PowerMockito.when(UtilityClass.staticMethod()).thenReturn("哈哈");
        String s = UtilityClass.staticMethod();
        System.out.println(s);
    }
    @Test
    public void  staticMethod3() {

        PowerMockito.when(UtilityClass.staticMethod()).thenReturn("哈哈");
        String s = UtilityClass.staticMethod();
        System.out.println(s);
    }
    @Test
    public void  staticMethod4() {

        PowerMockito.when(UtilityClass.staticMethod()).thenReturn("哈哈");
        String s = UtilityClass.staticMethod();
        System.out.println(s);
    }
    @Test
    public void  staticMethod5() {

        PowerMockito.when(UtilityClass.staticMethod()).thenReturn("哈哈");
        String s = UtilityClass.staticMethod();
        System.out.println(s);
    }
}