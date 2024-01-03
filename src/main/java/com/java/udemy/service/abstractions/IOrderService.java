package com.java.udemy.service.abstractions;

import com.java.udemy.request.OrderItemRequest;
import com.java.udemy.response.GetItemsByTransactionIdResponse;

public interface IOrderService {

    GetItemsByTransactionIdResponse getItemsByTransactionId(Integer orderId, Integer page);

    void addOrderItem(Integer orderId, OrderItemRequest orderItemRequest);

    void updateOrderItem(Integer orderId, Long itemId, OrderItemRequest orderItemRequest);

    void deleteOrderItem(Integer orderId, Long itemId);
}
