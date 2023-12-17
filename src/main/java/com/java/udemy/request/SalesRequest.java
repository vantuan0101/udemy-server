package com.java.udemy.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * NOT A TABLE
 * this is for customer's Purchase History
 */
@Getter
@ToString
@RequiredArgsConstructor
public class SalesRequest extends BaseRequest {
  private String transactionId;
  private Instant createdAt;
  private String paymentMethod;
  private BigDecimal totalPaid;
  private Long numOfItems;

  public SalesRequest(String transactionId, Instant createdAt, String paymentMethod, BigDecimal totalPaid,
      Long numOfItems) {
    this.transactionId = transactionId;
    this.createdAt = createdAt;
    this.paymentMethod = paymentMethod;
    this.totalPaid = totalPaid;
    this.numOfItems = numOfItems;
  }
}
