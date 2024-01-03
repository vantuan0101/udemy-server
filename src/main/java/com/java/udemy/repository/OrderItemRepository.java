package com.java.udemy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.udemy.models.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

  @Query("SELECT new com.java.udemy.request.OrderItemRequest(o.id, c.title, c.price) from OrderItem o " +
      "INNER JOIN Course c on o.course.id = c.id where o.sale.transactionId = ?1")
  List<Long> findByTransactionIdEquals(String transactionId);
}
