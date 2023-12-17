package com.java.udemy.service.abstractions;

import org.springframework.data.domain.Slice;

import com.java.udemy.request.OrderItemRequest;
import com.java.udemy.request.SalesRequest;

public interface ISalesService {
  Slice<SalesRequest> findByUserIdOrderByCreatedAtDesc(Integer userId, Integer page);

  Slice<OrderItemRequest> findByTransactionIdEquals(String transactionId, Integer page);
}
