package com.java.udemy.service.abstractions;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.java.udemy.models.Course;

public interface IWishlistService {
  int createWishlist(Integer courseId, Integer userId);

  Map<String, Boolean> checkIfExistWishlistNative(Integer userId, Integer courseId);

  Page<Course> getWishlistByUser(Integer userId, Integer page);

  int deleteByUserIdAndCoursesIn(Integer courseId, Integer userId);
}
