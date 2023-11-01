package com.java.udemy.custom;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic response with message
 */
@Getter
@Setter
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
