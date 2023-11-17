package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.CourseObjective;
import com.java.udemy.request.CreateObjectivesRequest;
import com.java.udemy.response.CreateObjectivesResponse;
import com.java.udemy.response.GetCourseObjectivesResponse;
import com.java.udemy.service.abstractions.IObjectivesService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/objectives", produces = MediaType.APPLICATION_JSON_VALUE)
public class ObjectivesController {

  @Autowired
  private IObjectivesService objectivesService;

  @PostMapping(value = "/")
  @Secured(value = "ROLE_ADMIN")
  public CreateObjectivesResponse addNewObjectives(@RequestBody @Valid CreateObjectivesRequest request) {
    try {
      List<CourseObjective> coList = objectivesService.createObjectives(request);
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
      List<CourseObjective> courseObjectivesList = objectivesService.getCourseObjectivesByCourseId(courseId);
      GetCourseObjectivesResponse response = new GetCourseObjectivesResponse();
      response.setGetCourseObjectives(courseObjectivesList);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }
}
