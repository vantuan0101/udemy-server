package com.java.udemy.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.udemy.custom.GenericResponse;
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

  @GetMapping(path = "/me")
  ResponseEntity<?> getUserById(@RequestBody Integer id) {
    try {
      UserDTO userDTO = userRepository.findUserDTObyId(id).orElseThrow();
      return ResponseEntity.status(HttpStatus.OK).body(
          userDTO);
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(
              GenericResponse.fail(ex.getMessage()));
    }
  }
}
