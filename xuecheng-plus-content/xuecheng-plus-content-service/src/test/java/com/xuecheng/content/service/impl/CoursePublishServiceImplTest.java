package com.xuecheng.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.mapper.CoursePublishPreMapper;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.CoursePreviewDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.model.po.CoursePublishPre;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.TeachplanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({/*JSON.class,XueChengPlusException.class*/}/*{CoursePublishServiceImpl.class}*/)
class CoursePublishServiceImplTest {

    @InjectMocks
    private CoursePublishServiceImpl service;

    @Mock
    private CourseBaseInfoService courseBaseInfoService;

    @Mock
    private TeachplanService teachplanService;

    @Mock
    private CourseBaseMapper courseBaseMapper;

    @Mock
    private CourseMarketMapper courseMarketMapper;

    @Mock
    private CoursePublishPreMapper coursePublishPreMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getCoursePreviewInfo() {
        // Arrange (准备数据)
        Long courseId = 1L;

        // 模拟 CourseBaseInfoService 的行为
        CourseBaseInfoDto courseBaseInfo = new CourseBaseInfoDto();
        when(courseBaseInfoService.getCourseBaseInfo(courseId)).thenReturn(courseBaseInfo);

        // 模拟 TeachplanService 的行为
        List<TeachplanDto> teachplanTree = Arrays.asList(new TeachplanDto(), new TeachplanDto());
        when(teachplanService.findTeachplanTree(courseId)).thenReturn(teachplanTree);

        // Act (调用被测方法)
        CoursePreviewDto result = service.getCoursePreviewInfo(courseId);
        System.out.println(result);
        // Assert (验证结果)
        assertNotNull(result);
        assertEquals(courseBaseInfo, result.getCourseBase());
        assertEquals(teachplanTree, result.getTeachplans());


    }

    @Test
    public void getCurrentTime() {

        String mockDate = "2023-10-01";
        when(service.getCurrentTime()).thenReturn(mockDate);
        assertEquals(mockDate, service.getCurrentTime());
    }

    @Test
    void commitAudit4() {
        // Arrange (准备数据)
        Long courseId = 1L;

        CourseBase courseBase = new CourseBase();
        courseBase.setAuditStatus("202003");
        courseBase.setCompanyId(2L);
        courseBase.setPic("http");
        when(courseBaseMapper.selectById(courseId)).thenReturn(courseBase);

        assertThrows(XueChengPlusException.class, () -> {
            service.commitAudit(2L, courseId);
        });

        courseBase.setAuditStatus("202004");
        assertThrows(XueChengPlusException.class, () -> {
            service.commitAudit(3L, courseId);
        });
        // 验证异常消息是否符合预期
       // assertEquals("当前为等待审核状态，审核完成可以再次提交。", exception.getMessage());
    }
    @Test
    void commitAudit3() {
        // Arrange (准备数据)
        Long companyId = 2L;
        Long courseId = 1L;

        CourseBase courseBase = new CourseBase();
        courseBase.setAuditStatus("202004");
        courseBase.setCompanyId(2L);
        courseBase.setPic("http");
        when(courseBaseMapper.selectById(courseId)).thenReturn(courseBase);
        String auditStatus = courseBase.getAuditStatus();

        XueChengPlusException exception = assertThrows(XueChengPlusException.class, () -> {
            service.commitAudit(companyId, courseId);
        });
        // 验证异常消息是否符合预期
        //assertEquals("当前为等待审核状态，审核完成可以再次提交。", exception.getMessage());
        //查询课程计划信息
        List<TeachplanDto> teachplanTree = Arrays.asList(new TeachplanDto(), new TeachplanDto());
        when(teachplanService.findTeachplanTree(courseId)).thenReturn(teachplanTree);

        //封装数据，基本信息、营销信息、课程计划信息、师资信息
        CoursePublishPre coursePublishPre = new CoursePublishPre();
        //查询基本信息
        CourseBaseInfoDto courseBaseInfo = new CourseBaseInfoDto();
        when(courseBaseInfoService.getCourseBaseInfo(courseId)).thenReturn(courseBaseInfo);
        BeanUtils.copyProperties(courseBaseInfo, coursePublishPre);
        //将课程计划信息转json
        String teachplanTreeJson = JSON.toJSONString(teachplanTree);
        coursePublishPre.setTeachplan(teachplanTreeJson);
        //课程营销信息
        CourseMarket courseMarket = new CourseMarket();
        when(courseMarketMapper.selectById(courseId)).thenReturn(courseMarket);
        //转为json
        String courseMarketJson = JSON.toJSONString(courseMarket);
        //将课程营销信息json数据放入课程预发布表
        coursePublishPre.setMarket(courseMarketJson);

        //课程预发布表初始审核状态
        coursePublishPre.setStatus("202003");

        CoursePublishPre coursePublishPre1 = new CoursePublishPre();
        when(coursePublishPreMapper.selectById(courseId)).thenReturn(coursePublishPre1);
        service.commitAudit(companyId, courseId);
    }

    @Test
    void testCommitAudit2() {
        // Arrange (准备数据)
        Long companyId = 2L;
        Long courseId = 1L;

        // 模拟 CourseBase 查询结果
        CourseBase courseBase = new CourseBase();
        courseBase.setAuditStatus("202004");
        courseBase.setCompanyId(2L);
        courseBase.setPic("http://example.com/image.png");
        when(courseBaseMapper.selectById(courseId)).thenReturn(courseBase);

        // 模拟课程计划查询结果
        List<TeachplanDto> teachplanTree = Arrays.asList(new TeachplanDto(), new TeachplanDto());
        when(teachplanService.findTeachplanTree(courseId)).thenReturn(teachplanTree);

        // 模拟课程基本信息查询结果
        CourseBaseInfoDto courseBaseInfo = new CourseBaseInfoDto();
        when(courseBaseInfoService.getCourseBaseInfo(courseId)).thenReturn(courseBaseInfo);

        // 模拟课程营销信息查询结果
        CourseMarket courseMarket = new CourseMarket();
        when(courseMarketMapper.selectById(courseId)).thenReturn(courseMarket);

        // 模拟 CoursePublishPre 查询结果（用于插入或更新）
        CoursePublishPre coursePublishPre1 = new CoursePublishPre();
        when(coursePublishPreMapper.selectById(courseId)).thenReturn(null); // 或者返回已存在的记录以测试更新逻辑

        // Act (调用待测试的方法)
        service.commitAudit(companyId, courseId);

        // Assert (验证 Mock 对象的方法是否按预期被调用)

        /*verify(courseBaseMapper).selectById(courseId);
        verify(teachplanService).findTeachplanTree(courseId);
        verify(courseBaseInfoService).getCourseBaseInfo(courseId);
        verify(courseMarketMapper).selectById(courseId);
        verify(coursePublishPreMapper).insert(any(CoursePublishPre.class)); // 如果是插入操作*/
        // 或者
        // verify(coursePublishPreMapper).updateById(any(CoursePublishPre.class)); // 如果是更新操作
    }

    @Test
    void publish() {
    }

    @Test
    void generateCourseHtml() {
    }

    @Test
    void uploadCourseHtml() {
    }

    @Test
    void saveCourseIndex() {
    }

    @Test
    void getCoursePublish() {
    }

    @Test
    void getCoursePublishCache() {
    }
}