package com.java.udemy.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private String message;
  private Boolean success;
  private String token;
  private Integer id;
  private String username;
  private String email;
  private List<String> roles;

  public LoginResponse(String message, Boolean success) {
    this.message = message;
    this.success = success;
    this.token = null;

  }

  public LoginResponse(String message, String token, Integer id, String username,
      String email, List<String> roles) {
    this.message = message;
    this.success = true;
    this.token = token;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  public LoginResponse(String message, Boolean success, String token) {
    this.message = message;
    this.success = success;
    this.token = token;
  }

  public static LoginResponse fail(String message) {
    return new LoginResponse(message, false);
  }
}
