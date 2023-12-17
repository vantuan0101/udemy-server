package com.java.udemy.repository;

import org.springframework.stereotype.Repository;
import com.java.udemy.models.User;
import com.java.udemy.request.UserRequest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  @Query("SELECT new com.java.udemy.request.UserRequest(id, fullname, email, createdAt) FROM User WHERE id = ?1")
  Optional<UserRequest> findUserDTObyId(Integer id);
}
