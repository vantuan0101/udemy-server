package com.java.udemy.service.abstractions;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.java.udemy.models.Course;

public interface ICartService {
  int addToCartCustom(Integer courseId, Integer userId);

  Map<String, Boolean> checkIfCourseInCart(Integer userId, Integer courseId);

  Page<Course> getCartListByUser(Integer userId, Integer page);

  Map<String, BigDecimal> getTotalBillForUser(Integer userId);

  Map<String, Long> countCartByUserIdEquals(Integer userId);

  int deleteByUserIdAndCoursesIn(Integer userId, Integer courseId);

}
