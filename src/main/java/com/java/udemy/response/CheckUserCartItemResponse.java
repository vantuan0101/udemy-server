package com.java.udemy.response;

import java.util.Map;

import lombok.Data;

@Data
public class CheckUserCartItemResponse {
  private Map<String, Boolean> checkUserCartItem;
}
