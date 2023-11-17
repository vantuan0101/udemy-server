package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.request.OrderItemRequest;
import com.java.udemy.request.SalesRequest;
import com.java.udemy.response.GetAllMyOwnedItemsResponse;
import com.java.udemy.response.GetItemsByTransactionIdResponse;
import com.java.udemy.service.abstractions.ISalesService;
import com.java.udemy.service.abstractions.IUserService;

import jakarta.servlet.http.HttpSession;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/sales", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesController {

  @Autowired
  private ISalesService salesService;

  @Autowired
  private IUserService userService;

  @GetMapping(path = "/mine")
  public GetAllMyOwnedItemsResponse getAllMyOwnedItems(@NotNull HttpSession session,
      @RequestParam(defaultValue = "0") Integer page) {
    try {
      Integer userId = userService.getSessionUserId(session);
      Slice<SalesRequest> allMyOwnedItems = salesService.findByUserIdOrderByCreatedAtDesc(userId, page);
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
      Slice<OrderItemRequest> itemsByTransactionId = salesService.findByTransactionIdEquals(transactionId, page);
      GetItemsByTransactionIdResponse response = new GetItemsByTransactionIdResponse();
      response.setGetItemsByTransactionId(itemsByTransactionId);
      return response;
    } catch (Exception ex) {
      throw new BadRequestException(ex.getMessage());
    }
  }

}
