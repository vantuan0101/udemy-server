package com.java.udemy.service.concretions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.models.Course;
import com.java.udemy.models.Review;
import com.java.udemy.models.User;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.ReviewRepository;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.ReviewRequest;
import com.java.udemy.service.abstractions.IReviewService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ReviewService implements IReviewService {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  /**
   * Insert new review.
   * Then, calculate and update AVG rating for course.
   * All in single DB transaction
   *
   * @param request from frontend
   * @param userId  userId
   */
  @Override
  @Transactional
  public void addCourseRating(ReviewRequest request, Integer userId) {
    User user = userRepository.findById(userId).orElseThrow();
    Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
    Review myReview = new Review(request.getRating(), request.getContent(), user, course);
    reviewRepository.save(myReview);

    // calculate and update average rating for course.
    double avgRating = reviewRepository.getAverageByCourseId(course.getId());
    course.setRating(BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.DOWN));
    courseRepository.save(course);
  }

  /**
   * EDIT existing review, and modify AVG course rating
   */
  @Override
  @Transactional
  public void updateCourseRating(Integer reviewId, ReviewRequest request) {
    Review myReview = reviewRepository.findById(reviewId).orElseThrow();
    Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
    myReview.setRating(request.getRating());
    myReview.setContent(request.getContent());
    reviewRepository.save(myReview);

    // calculate and update average rating for course.
    double avgRating = reviewRepository.getAverageByCourseId(course.getId());
    course.setRating(BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.DOWN));
    courseRepository.save(course);
  }

  @Override
  public Optional<Review> findByUserIdAndCourseId(Integer userId, Integer courseId) {
    Optional<Review> reviewOptional = reviewRepository.findByUserIdAndCourseId(userId, courseId);
    return reviewOptional;
  }

  @Override
  public Slice<ReviewRequest> findByCourseId(Integer page, String sortBy, Integer courseId) {
    if (Stream.of("createdAt", "rating").noneMatch(sortBy::equals)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid 'sort' param");
    }
    Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, sortBy);
    Slice<ReviewRequest> reviews = reviewRepository.findByCourseId(courseId, pageable);
    return reviews;
  }
}
