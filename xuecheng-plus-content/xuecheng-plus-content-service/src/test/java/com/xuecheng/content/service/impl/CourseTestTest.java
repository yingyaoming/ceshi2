package com.xuecheng.content.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(PowerMockRunner.class)
class CourseTestTest {

    @InjectMocks
    private CourseTest courseTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void get() {
        String input1 = "hello";
        String input3 = "he";

        String input2 = "world";
        //String actually1 = courseTest.get(input1, input2);
        //String actually2 = courseTest.get(input3, input2);
        //Assertions.assertEquals("helloworld", actually1);
        //Assertions.assertEquals("world", actually2);

    }


}