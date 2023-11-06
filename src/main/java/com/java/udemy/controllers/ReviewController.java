package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.custom.GenericResponse;
import com.java.udemy.dto.ReviewDTO;
import com.java.udemy.dto.ReviewRequest;
import com.java.udemy.models.Review;
import com.java.udemy.repository.ReviewRepository;
import com.java.udemy.service.ReviewService;
import com.java.udemy.service.concretions.UserService;

import jakarta.servlet.http.HttpSession;
import javax.validation.Valid;
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
  public ResponseEntity<GenericResponse> addCourseReview(@Valid @RequestBody ReviewRequest review,
      HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      reviewService.addCourseRating(review, userId);
      return ResponseEntity.ok().body(new GenericResponse("Thanks for your review!"));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add review", e);
    }
  }

  @PutMapping(path = "/id/{id}")
  @Secured(value = "ROLE_STUDENT")
  public ResponseEntity<GenericResponse> editCourseReview(@PathVariable Integer id,
      @Valid @RequestBody ReviewRequest review) {
    try {
      reviewService.updateCourseRating(id, review);
      return ResponseEntity.ok().body(new GenericResponse("Thanks for your review!"));
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not edit review", e);
    }
  }

  @GetMapping(path = "/mine/c/{courseId}")
  @Secured(value = "ROLE_STUDENT")
  public ResponseEntity<Review> getMyReviewOnCourse(@PathVariable Integer courseId, HttpSession session) {
    Integer userId = UserService.getSessionUserId(session);
    var reviewOptional = reviewRepository.findByUserIdAndCourseId(userId, courseId);
    return ResponseEntity.of(reviewOptional);
  }

  @GetMapping(path = "/course/{courseId}")
  public Slice<ReviewDTO> getCourseReviews(@RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @PathVariable Integer courseId) {
    if (Stream.of("createdAt", "rating").noneMatch(sortBy::equals)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid 'sort' param");
    }
    Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, sortBy);
    return reviewRepository.findByCourseId(courseId, pageable);
  }
}
