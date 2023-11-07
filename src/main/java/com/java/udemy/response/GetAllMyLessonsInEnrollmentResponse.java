package com.java.udemy.response;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class GetAllMyLessonsInEnrollmentResponse {
  private List<Map<String, Object>> getAllMyLessonsInEnrollment;

}
