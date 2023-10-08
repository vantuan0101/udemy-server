package com.java.udemy.repository;

import org.springframework.stereotype.Repository;
import com.java.udemy.dto.UserDTO;
import com.java.udemy.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  @Query("SELECT new com.java.udemy.dto.UserDTO(id, fullname, email, createdAt) FROM User WHERE id = ?1")
  Optional<UserDTO> findUserDTObyId(Integer id);
}
