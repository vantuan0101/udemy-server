package com.java.udemy.service.concretions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.models.Course;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.request.CategoryRequest;
import com.java.udemy.service.abstractions.ICourseService;

@Service
public class CourseService implements ICourseService {
  @Autowired
  public CourseRepository courseRepository;

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
  public Course insertCourse(Course course) {
    // You may want to perform additional validation or business logic here before saving.
    // Set a default rating if it is not provided
    course.setRating(course.getRating() != null ? course.getRating() : BigDecimal.ZERO);
    return courseRepository.save(course);
  }

  @Override
  public Course updateCourse(Integer id, Course updatedCourse) {
    Course existingCourse = courseRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    
    // Update the fields of the existing course with the values from the updated course.
    existingCourse.setTitle(updatedCourse.getTitle());
    existingCourse.setSubtitle(updatedCourse.getSubtitle());
    existingCourse.setAuthor(updatedCourse.getAuthor());
    existingCourse.setCategory(updatedCourse.getCategory());
    
    existingCourse.setThumbUrl(updatedCourse.getThumbUrl());
    existingCourse.setPrice(updatedCourse.getPrice());
    existingCourse.setIsFeatured(updatedCourse.getIsFeatured());

    // Save the updated course.
    return courseRepository.save(existingCourse);
  }

  @Override
  public void deleteCourse(Integer id) {
    try {
      // Attempt to delete the course by ID.
      courseRepository.deleteById(id);
    } catch (EmptyResultDataAccessException ex) {
      // If the course doesn't exist, throw a 404 Not Found exception.
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
    }
  }

}
