package com.java.udemy.service.abstractions;

import java.util.List;

import com.java.udemy.models.CourseObjective;
import com.java.udemy.request.CreateObjectivesRequest;

public interface IObjectivesService {
  List<CourseObjective> createObjectives(CreateObjectivesRequest request, Integer userId);

  void deleteObjectives(Integer objectiveId, Integer userId);

  List<CourseObjective> getCourseObjectivesByCourseId(Integer courseId);
}
