package com.java.udemy.response;

import java.util.Optional;

import com.java.udemy.models.Review;

import lombok.Data;

@Data
public class GetMyReviewOnCourseResponse {
  Optional<Review> review;
}
