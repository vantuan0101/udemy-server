package com.java.udemy.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import com.java.udemy.custom.GenericResponse;
import com.java.udemy.dto.UserDTO;
import com.java.udemy.models.User;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.service.concretions.UserService;

import jakarta.servlet.http.HttpSession;

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
  ResponseEntity<UserDTO> getUserById(@NotNull HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      System.out.println("userId" + userId);
      UserDTO userDTO = userRepository.findUserDTObyId(userId).orElseThrow();
      return ResponseEntity.status(HttpStatus.OK).body(
          userDTO);
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    }
  }

  @PutMapping(path = "/me")
  @Transactional
  public ResponseEntity<UserDTO> editMyProfile(@RequestBody UserDTO userDTO, @NotNull HttpSession session) {
    try {
      Integer userId = UserService.getSessionUserId(session);
      User u = userRepository.findById(userId).orElseThrow();
      u.setFullname(userDTO.getFullname());
      // You may modify other fields
      u.setConfirmPass("WHATEVER!");
      User freshUser = userRepository.save(u);
      return ResponseEntity.ok().body(modelMapper.map(freshUser, UserDTO.class));
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not edit your profile", ex);
    }
  }

  // @GetMapping(path = "/summar
  // @ResponseStatus(value = HttpStatus.
  // @Cacheable(value = "student-summary", key = "#session.i
  // public List<StudentSummary> getUserSummary(@NotNull HttpSession session
  // Integer userId = MyUserDetailsService.getSessionUserId(sessio
  // User user = userRepository.findById(userId).orElseThrow(() -> new
  // ResponseStatusException(HttpStatus.NOT_FOUND
  // return profileService.getUserSummaryList(use
  // }
}
