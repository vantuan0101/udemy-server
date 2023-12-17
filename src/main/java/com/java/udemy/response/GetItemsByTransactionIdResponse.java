package com.java.udemy.response;

import org.springframework.data.domain.Slice;

import com.java.udemy.request.OrderItemRequest;

import lombok.Data;

@Data
public class GetItemsByTransactionIdResponse {
  private Slice<OrderItemRequest> getItemsByTransactionId;
}
