package com.java.udemy.response;

import lombok.Data;

/**
 * Generic response with message
 */
@Data
public class GenericResponse {
  private String message;
  private Boolean success;

  public GenericResponse(String message, Boolean success) {
    this.message = message;
    this.success = success;
  }

  public GenericResponse(String message) {
    this.message = message;
    this.success = true;
  }

  public static GenericResponse fail(String message) {
    return new GenericResponse(message, false);
  }

}
