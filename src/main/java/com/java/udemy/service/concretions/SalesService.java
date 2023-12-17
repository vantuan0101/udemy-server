package com.java.udemy.service.concretions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.java.udemy.repository.OrderItemRepository;
import com.java.udemy.repository.SalesRepository;
import com.java.udemy.request.OrderItemRequest;
import com.java.udemy.request.SalesRequest;
import com.java.udemy.service.abstractions.ISalesService;

@Service
public class SalesService implements ISalesService {
  @Autowired
  private SalesRepository salesRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Override
  public Slice<SalesRequest> findByUserIdOrderByCreatedAtDesc(Integer userId, Integer page) {
    Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdAt");
    Slice<SalesRequest> allMyOwnedItems = salesRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    return allMyOwnedItems;
  }

  @Override
  public Slice<OrderItemRequest> findByTransactionIdEquals(String transactionId, Integer page) {
    Slice<OrderItemRequest> itemsByTransactionId = orderItemRepository.findByTransactionIdEquals(transactionId,
        PageRequest.of(page, 10));
    return itemsByTransactionId;
  }
}
