package com.java.udemy.response;

import org.springframework.data.domain.Page;

import com.java.udemy.models.Course;

import lombok.Data;

@Data
public class GetAllMyCartItemsResponse {
  private Page<Course> getAllMyCartItems;
}
