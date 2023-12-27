package com.java.udemy.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CourseRequest {
  private String title;

  private String subtitle;

  private String category;

  private BigDecimal rating;

  private String thumbUrl;

  private BigDecimal price;
}
