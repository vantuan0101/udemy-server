package com.java.udemy.response;

import com.java.udemy.request.UserRequest;

import lombok.Data;

@Data
public class EditMyProfileResponse {
  UserRequest userDTO;
}
