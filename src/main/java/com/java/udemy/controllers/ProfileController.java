package com.java.udemy.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.dto.UserDTO;
import com.java.udemy.repository.UserRepository;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public ProfileController(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.modelMapper = new ModelMapper();
  }

  @GetMapping(path = "/me/{id}")
  ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
    try {
      UserDTO userDTO = userRepository.findUserDTObyId(id).orElseThrow();
      return ResponseEntity.ok().body(userDTO);

    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    }
  }
}
