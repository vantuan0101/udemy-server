package com.java.udemy.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.udemy.dto.OrderItemDTO;
import com.java.udemy.models.OrderItem;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

  @Query("SELECT new com.java.udemy.dto.OrderItemDTO(o.id, c.title, c.price) from OrderItem o " +
      "INNER JOIN Course c on o.course.id = c.id where o.sale.transactionId = ?1")
  Slice<OrderItemDTO> findByTransactionIdEquals(String transactionId, Pageable pageable);

}
