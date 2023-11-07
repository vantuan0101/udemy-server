package com.java.udemy.response;

import com.java.udemy.request.UserRequest;

import lombok.Data;

@Data
public class GetUserByIdResponse {
  private UserRequest userDTO;
}
