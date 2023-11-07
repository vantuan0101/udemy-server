package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.WishlistRepository;
import com.java.udemy.response.CheckUserLikedCourseResponse;
import com.java.udemy.response.GenericResponse;
import com.java.udemy.response.GetMyWishlistPagedResponse;
import com.java.udemy.service.concretions.UserService;

import jakarta.servlet.http.HttpSession;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

@RestController
@Secured("ROLE_STUDENT")
@RequestMapping(path = "/wishlist", produces = MediaType.APPLICATION_JSON_VALUE)
public class WishlistController {

  @Autowired
  private WishlistRepository wishlistRepository;

  @Autowired
  private CourseRepository courseRepository;

  @PostMapping(path = "/course/{courseId}")
  @ResponseStatus(HttpStatus.CREATED)
  public GenericResponse addNewWishlist(@PathVariable Integer courseId, HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      int count = wishlistRepository.saveByCourseIdAndUserId(courseId, userId);
      GenericResponse response = new GenericResponse(String.format("Added %d item to Wishlist", count));
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @GetMapping(path = "/status/c/{courseId}")
  @ResponseStatus(HttpStatus.OK)
  public CheckUserLikedCourseResponse checkUserLikedCourse(@PathVariable @NotNull Integer courseId,
      HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      boolean inWishlist = wishlistRepository.checkIfExistWishlistNative(userId, courseId) > 0;
      Map<String, Boolean> userLikedCourse = Collections.singletonMap("inWishlist", inWishlist);
      CheckUserLikedCourseResponse response = new CheckUserLikedCourseResponse();
      response.setCheckUserLikedCourse(userLikedCourse);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @GetMapping(path = "/mine")
  @ResponseStatus(HttpStatus.OK)
  public GetMyWishlistPagedResponse getMyWishlistPaged(@RequestParam(defaultValue = "0") Integer page,
      HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      Page<Course> listCourse = courseRepository.getWishlistByUser(userId, PageRequest.of(Math.abs(page), 5));
      GetMyWishlistPagedResponse response = new GetMyWishlistPagedResponse();
      response.setGetMyWishlistPaged(listCourse);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @DeleteMapping(path = "/course/{courseId}")
  @ResponseStatus(HttpStatus.OK)
  public GenericResponse removeWishlistByCourseId(@PathVariable @NotNull Integer courseId,
      HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      int deletedCount = wishlistRepository.deleteByUserIdAndCoursesIn(userId, Collections.singletonList(courseId));
      if (deletedCount != 1) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not remove from wishlist");
      }
      GenericResponse response = new GenericResponse("Removed from Wishlist, course " + courseId);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }
}
