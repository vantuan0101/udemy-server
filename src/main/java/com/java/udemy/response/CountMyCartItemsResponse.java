package com.java.udemy.response;

import java.util.Map;

import lombok.Data;

@Data
public class CountMyCartItemsResponse {
  private Map<String, Long> countMyCartItems;
}
