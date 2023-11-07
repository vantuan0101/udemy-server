package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Review;
import com.java.udemy.repository.ReviewRepository;
import com.java.udemy.request.ReviewRequest;
import com.java.udemy.response.GenericResponse;
import com.java.udemy.response.GetCourseReviewsResponse;
import com.java.udemy.response.GetMyReviewOnCourseResponse;
import com.java.udemy.service.concretions.ReviewService;
import com.java.udemy.service.concretions.UserService;

import jakarta.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private ReviewService reviewService;

  @PostMapping(path = "/")
  @Secured(value = "ROLE_STUDENT")
  public GenericResponse addCourseReview(@Valid @RequestBody ReviewRequest review,
      HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      reviewService.addCourseRating(review, userId);
      GenericResponse response = new GenericResponse("Thanks for your review!");
      return response;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add review", e);
    }
  }

  @PutMapping(path = "/id/{id}")
  @Secured(value = "ROLE_STUDENT")
  public GenericResponse editCourseReview(@PathVariable Integer id,
      @Valid @RequestBody ReviewRequest review) {
    try {
      reviewService.updateCourseRating(id, review);
      GenericResponse response = new GenericResponse("Thanks for your review!");
      return response;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not edit review", e);
    }
  }

  @GetMapping(path = "/mine/c/{courseId}")
  @Secured(value = "ROLE_STUDENT")
  public GetMyReviewOnCourseResponse getMyReviewOnCourse(@PathVariable Integer courseId, HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      Optional<Review> reviewOptional = reviewRepository.findByUserIdAndCourseId(userId, courseId);
      GetMyReviewOnCourseResponse response = new GetMyReviewOnCourseResponse();
      response.setReview(reviewOptional);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @GetMapping(path = "/course/{courseId}")
  public GetCourseReviewsResponse getCourseReviews(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @PathVariable Integer courseId) {
    try {
      if (Stream.of("createdAt", "rating").noneMatch(sortBy::equals)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid 'sort' param");
      }
      Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, sortBy);
      Slice<ReviewRequest> reviews = reviewRepository.findByCourseId(courseId, pageable);
      GetCourseReviewsResponse response = new GetCourseReviewsResponse();
      response.setGetCourseReviews(reviews);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }
}
