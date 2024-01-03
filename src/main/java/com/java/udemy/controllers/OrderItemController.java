package com.java.udemy.controllers;

import com.java.udemy.models.OrderItem;
import com.java.udemy.repository.OrderItemRepository;
import com.java.udemy.request.OrderItemRequest;
import com.java.udemy.response.OrderItemResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping
    public List<Long> getCourseIdsByTransactionId(@RequestParam String transactionId) {
        return orderItemRepository.findByTransactionIdEquals(transactionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItemById(@PathVariable Integer id) {
        return orderItemRepository.findById(id)
                .map(orderItem -> {
                    OrderItemResponse response = new OrderItemResponse(
                            orderItem.getId(),
                            orderItem.getCourseId(),
                            orderItem.getTransactionId()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<OrderItem> addOrderItem(@RequestBody OrderItemRequest orderItemRequest) {
        OrderItem orderItem = new OrderItem();
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return ResponseEntity.ok(savedOrderItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Integer id, @RequestBody OrderItemRequest orderItemRequest) {
        return orderItemRepository.findById(id)
                .map(existingOrderItem -> {
                    OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
                    return ResponseEntity.ok(updatedOrderItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Integer id) {
        return orderItemRepository.findById(id)
                .map(existingOrderItem -> {
                    orderItemRepository.delete(existingOrderItem);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
