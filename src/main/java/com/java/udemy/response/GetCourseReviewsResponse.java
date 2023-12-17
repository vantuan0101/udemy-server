package com.java.udemy.response;

import org.springframework.data.domain.Slice;

import com.java.udemy.request.ReviewRequest;

import lombok.Data;

@Data
public class GetCourseReviewsResponse {
  private Slice<ReviewRequest> getCourseReviews;
}
