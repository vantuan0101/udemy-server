package com.java.udemy.response;

import java.util.Map;

import lombok.Data;

@Data
public class CheckUserLikedCourseResponse {
  private Map<String, Boolean> checkUserLikedCourse;
}
