package com.java.udemy.service.concretions;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.models.Course;
import com.java.udemy.models.User;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.CategoryRequest;
import com.java.udemy.request.CourseRequest;
import com.java.udemy.service.abstractions.ICourseService;

@Service
public class CourseService implements ICourseService {
  @Autowired
  public CourseRepository courseRepository;

  @Autowired
  public UserRepository userRepository;

  @Override
  public Optional<Course> findCourseById(Integer id) {
    return courseRepository.findById(id);
  }

  @Override
  public List<Course> getCoursesByCategoryEquals(String category) {
    List<Course> courseList = courseRepository.getCoursesByCategoryEquals(category);
    if (courseList.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results for given category");
    }
    return courseList;
  }

  @Override
  public List<Course> getTop6CoursesByIsFeatured(Boolean isFeatured) {
    List<Course> courseList = courseRepository.getTop6CoursesByIsFeatured(isFeatured);
    return courseList;
  }

  @Override
  public List<CategoryRequest> getAllDistinctCategories() {
    List<CategoryRequest> categories = courseRepository.getAllDistinctCategories();
    return categories;
  }

  @Override
  public Slice<Course> getCoursesByTitleContaining(String title, Integer page) {
    if (title.length() < 3) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search query too short");
    }
    Slice<Course> searchForCourseByTitles = courseRepository.getCoursesByTitleContaining(title,
        PageRequest.of(page, 10));
    return searchForCourseByTitles;

  }

  @Override
  public Course createCourse(CourseRequest request, Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    Course courseModel = new Course(
        request.getTitle(),
        request.getSubtitle(),
        user,
        request.getCategory(),
        request.getThumbUrl(),
        request.getPrice());
    Course newCourse = courseRepository.save(courseModel);
    return newCourse;
  }

  @Override
  public Course updateCourse(CourseRequest request, Integer userId, Integer courseId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    Course course = courseRepository.findById(courseId).orElseThrow();
    if (!user.getId().equals(course.getUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
    }
    course.setTitle(request.getTitle());
    course.setSubtitle(request.getSubtitle());
    course.setCategory(request.getCategory());
    course.setRating(request.getRating());
    course.setThumbUrl(request.getThumbUrl());
    course.setPrice(request.getPrice());
    return courseRepository.save(course);
  }

  @Override
  public void deleteCourse(Integer courseId, Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    Course course = courseRepository.findById(courseId).orElseThrow();
    if (!user.getId().equals(course.getUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
    }
    courseRepository.deleteById(courseId);
  }

}
