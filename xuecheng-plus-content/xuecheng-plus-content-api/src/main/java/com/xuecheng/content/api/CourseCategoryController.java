package com.xuecheng.content.api;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description TODO
 * @author Mr.M
 * @date 2022/10/8 11:25
 * @version 1.0
 */
@Api(value = "课程分类查询接口",tags = "课程分类查询接口")
 @RestController
public class CourseCategoryController {

    @Autowired
    CourseCategoryService CourseCategoryService;

    @Resource
    private CourseCategoryMapper courseCategoryMapper;

     @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes(){


      return CourseCategoryService.queryTreeNodes();
    }

    @GetMapping("/course-category/tree-nodes-New")
    public List<CourseCategoryTreeDto> queryTreeNodesNew(){


        return CourseCategoryService.queryTreeNodesNew();
    }

    @GetMapping("/course-category/{id}")
    public CourseCategory queryTreeNodesById(@PathVariable String id){

        return courseCategoryMapper.selectById(id);
    }
}
