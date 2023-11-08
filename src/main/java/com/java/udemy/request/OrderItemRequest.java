package com.java.udemy.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class OrderItemRequest extends BaseRequest {
  private Long id;
  private String title;
  private BigDecimal price;

  public OrderItemRequest(Long id, String title, BigDecimal price) {
    this.id = id;
    this.title = title;
    this.price = price;
  }
}
