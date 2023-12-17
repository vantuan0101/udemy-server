package com.java.udemy.service.concretions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.udemy.models.Course;
import com.java.udemy.models.CourseObjective;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.ObjectiveRepository;
import com.java.udemy.request.CreateObjectivesRequest;
import com.java.udemy.service.abstractions.IObjectivesService;

@Service
public class ObjectivesService implements IObjectivesService {
  @Autowired
  private ObjectiveRepository objectiveRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Override
  public List<CourseObjective> createObjectives(CreateObjectivesRequest request) {
    List<String> objectives = request.getObjectives();
    Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
    List<CourseObjective> coList = objectives.stream().map(o -> new CourseObjective(course, o))
        .collect(Collectors.toList());
    objectiveRepository.saveAll(coList);
    return coList;
  }

  @Override
  public List<CourseObjective> getCourseObjectivesByCourseId(Integer courseId) {
    List<CourseObjective> courseObjectivesList = objectiveRepository.getCourseObjectivesByCourseId(courseId);
    return courseObjectivesList;
  }
}
