package com.java.udemy.service.abstractions;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Slice;

import com.java.udemy.models.Course;
import com.java.udemy.request.CategoryRequest;
import com.java.udemy.request.CourseRequest;

public interface ICourseService {
  Optional<Course> findCourseById(Integer id);

  List<Course> getCoursesByCategoryEquals(String category);

  List<Course> getTop6CoursesByIsFeatured(Boolean isFeatured);

  List<CategoryRequest> getAllDistinctCategories();

  Slice<Course> getCoursesByTitleContaining(String title, Integer page);

  Course createCourse(CourseRequest request, Integer userId);

  Course updateCourse(CourseRequest request, Integer userId, Integer courserId);

  void deleteCourse(Integer courseId, Integer userId);
}
