package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @author Mr.M
 * @date 2022/10/8 15:05
 * @version 1.0
 */
@Slf4j
 @Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

 @Autowired
 CourseCategoryMapper courseCategoryMapper;


 @Override
 public List<CourseCategoryTreeDto> queryTreeNodes() {
  return courseCategoryMapper.selectTreeNodes();
 }

 @Override
 public List<CourseCategoryTreeDto> queryTreeNodesNew() {
  //获取所有节点
  List<CourseCategory> courseCategories = courseCategoryMapper.selectList(null);

  //封装为CourseCategoryTreeDto对象
  List<CourseCategoryTreeDto> treeDtoList = courseCategories.stream().map(item -> {
   CourseCategoryTreeDto courseCategoryTreeDto = new CourseCategoryTreeDto();
   BeanUtils.copyProperties(item, courseCategoryTreeDto);
   return courseCategoryTreeDto;
  }).collect(Collectors.toList());

  //创建最终结果集
  ArrayList<CourseCategoryTreeDto> list = new ArrayList<>();

  treeDtoList.forEach(item -> {
   if ("1".equals(item.getParentid())){
    //递归查找子分类
    item.setChildrenTreeNodes(getChildrenTreeNodesNew(item, treeDtoList));
    list.add(item);
   }
  });
  return list;
 }

 /**
  * 根据当前id递归获取子集
  * @param itself
  * @param treeDtoList
  * @return
  */
 private List<CourseCategoryTreeDto> getChildrenTreeNodes(CourseCategoryTreeDto itself, List<CourseCategoryTreeDto> treeDtoList){

  //创建结果集
  ArrayList<CourseCategoryTreeDto> treeDtoArrayList = new ArrayList<>();

  treeDtoList.forEach(item -> {
   if (itself.getId().equals(item.getParentid())){
    itself.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
    List<CourseCategoryTreeDto> childrenTreeNodes = getChildrenTreeNodes(item, treeDtoList);

    //如果存在下级节点
    if (childrenTreeNodes.size() > 0){
     item.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
     item.getChildrenTreeNodes().addAll(childrenTreeNodes);
    }

    treeDtoArrayList.add(item);
   }
  });
  return treeDtoArrayList;
 }
 private List<CourseCategoryTreeDto> getChildrenTreeNodesNew(CourseCategoryTreeDto itself, List<CourseCategoryTreeDto> treeDtoList) {
  List<CourseCategoryTreeDto> children = treeDtoList.stream()
          .filter(item -> Objects.equals(itself.getId(), item.getParentid()))
          .peek(item -> item.setChildrenTreeNodes(getChildrenTreeNodes(item, treeDtoList)))
          .collect(Collectors.toList());

  return children;
 }
}
