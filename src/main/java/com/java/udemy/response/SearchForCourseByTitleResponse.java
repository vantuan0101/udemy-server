package com.java.udemy.response;

import org.springframework.data.domain.Slice;

import com.java.udemy.models.Course;

import lombok.Data;

@Data
public class SearchForCourseByTitleResponse {
  private Slice<Course> searchForCourseByTitle;
}
