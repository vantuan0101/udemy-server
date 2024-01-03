package com.java.udemy.service.abstractions;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.java.udemy.request.OrderItemRequest;
import com.java.udemy.request.SalesRequest;

public interface ISalesService {
  Slice<SalesRequest> findByUserIdOrderByCreatedAtDesc(Integer userId, Integer page);

  List<Long> findByTransactionIdEquals(String transactionId);
}
