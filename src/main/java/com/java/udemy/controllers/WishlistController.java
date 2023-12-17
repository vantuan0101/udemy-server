package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.response.CheckUserLikedCourseResponse;
import com.java.udemy.response.GenericResponse;
import com.java.udemy.response.GetMyWishlistPagedResponse;
import com.java.udemy.service.abstractions.IUserService;
import com.java.udemy.service.abstractions.IWishlistService;

import jakarta.servlet.http.HttpSession;

import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@Secured("ROLE_STUDENT")
@RequestMapping(path = "/wishlist", produces = MediaType.APPLICATION_JSON_VALUE)
public class WishlistController {

  @Autowired
  private IWishlistService wishlistService;

  @Autowired
  private IUserService userService;

  @PostMapping(path = "/course/{courseId}")
  @ResponseStatus(HttpStatus.CREATED)
  public GenericResponse addNewWishlist(@PathVariable Integer courseId, HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      int count = wishlistService.createWishlist(courseId, userId);
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
      Integer userId = userService.getSessionUserId(session);
      Map<String, Boolean> userLikedCourse = wishlistService.checkIfExistWishlistNative(userId, courseId);
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
      Integer userId = userService.getSessionUserId(session);
      Page<Course> listCourse = wishlistService.getWishlistByUser(userId, page);
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
      Integer userId = userService.getSessionUserId(session);
      int deletedCount = wishlistService.deleteByUserIdAndCoursesIn(userId, courseId);
      GenericResponse response = new GenericResponse("Removed from Wishlist, course " + courseId);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }
}
