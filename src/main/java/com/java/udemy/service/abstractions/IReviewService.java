package com.java.udemy.service.abstractions;

import com.java.udemy.request.ReviewRequest;

public interface IReviewService {
  void addCourseRating(ReviewRequest request, Integer userId);

  void updateCourseRating(Integer reviewId, ReviewRequest request);
}
