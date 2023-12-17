package com.java.udemy.request;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * REVIEW body sent from frontend
 */
@Data
public class ReviewRequest extends BaseRequest {

  @NotNull
  @Min(value = 1, message = "rating cannot be below 1")
  @Max(value = 5)
  private Integer rating;

  @Size(max = 300)
  @NotEmpty
  private String content;

  @NotNull
  private Integer courseId;

}
