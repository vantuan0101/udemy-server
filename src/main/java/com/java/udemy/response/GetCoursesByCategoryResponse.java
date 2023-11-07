package com.java.udemy.response;

import java.util.List;

import com.java.udemy.models.Course;

import lombok.Data;

@Data
public class GetCoursesByCategoryResponse {
  private List<Course> getCoursesByCategory;
}
