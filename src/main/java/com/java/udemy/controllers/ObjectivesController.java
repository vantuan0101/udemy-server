package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.models.CourseObjective;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.ObjectiveRepository;
import com.java.udemy.request.CreateObjectivesRequest;
import com.java.udemy.response.CreateObjectivesResponse;
import com.java.udemy.response.GetCourseObjectivesResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/objectives", produces = MediaType.APPLICATION_JSON_VALUE)
public class ObjectivesController {

  @Autowired
  private ObjectiveRepository objectiveRepository;

  @Autowired
  private CourseRepository courseRepository;

  @PostMapping(value = "/")
  @Secured(value = "ROLE_ADMIN")
  public CreateObjectivesResponse addNewObjectives(@RequestBody @Valid CreateObjectivesRequest request) {
    List<String> objectives = request.getObjectives();
    try {
      Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
      List<CourseObjective> coList = objectives.stream().map(o -> new CourseObjective(course, o))
          .collect(Collectors.toList());
      objectiveRepository.saveAll(coList);
      CreateObjectivesResponse response = new CreateObjectivesResponse();
      response.setMessage("All saved!");
      return response;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save new objectives", e);
    }
  }

  @GetMapping(value = "/course/{courseId}")
  public GetCourseObjectivesResponse getCourseObjectives(@PathVariable Integer courseId) {
    try {
      List<CourseObjective> courseObjectivesList = objectiveRepository.getCourseObjectivesByCourseId(courseId);
      GetCourseObjectivesResponse response = new GetCourseObjectivesResponse();
      response.setGetCourseObjectives(courseObjectivesList);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }
}
