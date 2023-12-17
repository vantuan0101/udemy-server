package com.java.udemy.response;

import java.util.List;

import com.java.udemy.models.CourseObjective;

import lombok.Data;

@Data
public class GetCourseObjectivesResponse {
  private List<CourseObjective> getCourseObjectives;
}
