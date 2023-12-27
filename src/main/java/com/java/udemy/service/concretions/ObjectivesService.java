package com.java.udemy.service.concretions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.models.Course;
import com.java.udemy.models.CourseObjective;
import com.java.udemy.models.User;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.ObjectiveRepository;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.CreateObjectivesRequest;
import com.java.udemy.service.abstractions.IObjectivesService;

@Service
public class ObjectivesService implements IObjectivesService {
  @Autowired
  private ObjectiveRepository objectiveRepository;

  @Autowired
  public CourseRepository courseRepository;

  @Autowired
  public UserRepository userRepository;

  @Override
  public List<CourseObjective> createObjectives(CreateObjectivesRequest request, Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
    if (!user.getId().equals(course.getUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
    }
    List<String> objectives = request.getObjectives();
    List<CourseObjective> coList = objectives.stream().map(o -> new CourseObjective(course, user, o))
        .collect(Collectors.toList());
    objectiveRepository.saveAll(coList);
    return coList;
  }

  @Override
  public void deleteObjectives(Integer objectiveId, Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    objectiveRepository.deleteById(objectiveId);
  }

  @Override
  public List<CourseObjective> getCourseObjectivesByCourseId(Integer courseId) {
    List<CourseObjective> courseObjectivesList = objectiveRepository.getCourseObjectivesByCourseId(courseId);
    return courseObjectivesList;
  }
}
