package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.repository.OrderItemRepository;
import com.java.udemy.repository.SalesRepository;
import com.java.udemy.request.OrderItemRequest;
import com.java.udemy.request.SalesRequest;
import com.java.udemy.response.GetAllMyOwnedItemsResponse;
import com.java.udemy.response.GetItemsByTransactionIdResponse;
import com.java.udemy.service.concretions.UserService;

import jakarta.servlet.http.HttpSession;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/sales", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesController {

  @Autowired
  private SalesRepository salesRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @GetMapping(path = "/mine")
  public GetAllMyOwnedItemsResponse getAllMyOwnedItems(@NotNull HttpSession session,
      @RequestParam(defaultValue = "0") Integer page) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      Pageable pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdAt");
      Slice<SalesRequest> allMyOwnedItems = salesRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
      GetAllMyOwnedItemsResponse response = new GetAllMyOwnedItemsResponse();
      response.setGetAllMyOwnedItems(allMyOwnedItems);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

  @GetMapping(path = "/mine/{transactionId}")
  public GetItemsByTransactionIdResponse getItemsByTransactionId(@PathVariable String transactionId,
      @RequestParam(defaultValue = "0") Integer page) {
    try {
      Slice<OrderItemRequest> itemsByTransactionId = orderItemRepository.findByTransactionIdEquals(transactionId,
          PageRequest.of(page, 10));
      GetItemsByTransactionIdResponse response = new GetItemsByTransactionIdResponse();
      response.setGetItemsByTransactionId(itemsByTransactionId);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

}
