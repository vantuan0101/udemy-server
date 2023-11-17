package com.java.udemy.service.abstractions;

import java.util.Optional;

import org.springframework.data.domain.Slice;

import com.java.udemy.models.Review;
import com.java.udemy.request.ReviewRequest;

public interface IReviewService {
  void addCourseRating(ReviewRequest request, Integer userId);

  void updateCourseRating(Integer reviewId, ReviewRequest request);

  Optional<Review> findByUserIdAndCourseId(Integer userId, Integer courseId);

  Slice<ReviewRequest> findByCourseId(Integer page, String sortBy, Integer courseId);
}
