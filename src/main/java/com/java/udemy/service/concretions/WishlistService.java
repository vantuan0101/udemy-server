package com.java.udemy.service.concretions;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.models.Course;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.WishlistRepository;
import com.java.udemy.service.abstractions.IWishlistService;

@Service
public class WishlistService implements IWishlistService {
  @Autowired
  private WishlistRepository wishlistRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public int createWishlist(Integer courseId, Integer userId) {
    int count = wishlistRepository.saveByCourseIdAndUserId(courseId, userId);
    return count;
  }

  @Override
  public Map<String, Boolean> checkIfExistWishlistNative(Integer userId, Integer courseId) {
    boolean inWishlist = wishlistRepository.checkIfExistWishlistNative(userId, courseId) > 0;
    Map<String, Boolean> userLikedCourse = Collections.singletonMap("inWishlist", inWishlist);
    return userLikedCourse;
  }

  @Override
  public Page<Course> getWishlistByUser(Integer userId, Integer page) {
    Page<Course> listCourse = courseRepository.getWishlistByUser(userId, PageRequest.of(Math.abs(page), 5));
    return listCourse;
  }

  @Override
  public int deleteByUserIdAndCoursesIn(Integer courseId, Integer userId) {
    int deletedCount = wishlistRepository.deleteByUserIdAndCoursesIn(userId, Collections.singletonList(courseId));
    if (deletedCount != 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not remove from wishlist");
    }
    return deletedCount;
  }
}
