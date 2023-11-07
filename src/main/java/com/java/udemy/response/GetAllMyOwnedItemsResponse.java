package com.java.udemy.response;

import org.springframework.data.domain.Slice;

import com.java.udemy.request.SalesRequest;

import lombok.Data;

@Data
public class GetAllMyOwnedItemsResponse {
  private Slice<SalesRequest> getAllMyOwnedItems;
}
