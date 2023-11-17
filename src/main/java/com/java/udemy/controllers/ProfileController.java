package com.java.udemy.controllers;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import com.java.udemy.models.User;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.UserRequest;
import com.java.udemy.response.EditMyProfileResponse;
import com.java.udemy.response.GetUserByIdResponse;
import com.java.udemy.service.abstractions.IUserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private IUserService userService;

  @Autowired
  public ProfileController(UserRepository userRepository, IUserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.modelMapper = new ModelMapper();
  }

  @GetMapping(path = "/me")
  public GetUserByIdResponse getUserById(@NotNull HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      UserRequest userDTO = userRepository.findUserDTObyId(userId).orElseThrow();
      GetUserByIdResponse response = new GetUserByIdResponse();
      response.setUserDTO(userDTO);
      return response;
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
  }

  @PutMapping(path = "/me")
  @Transactional
  public EditMyProfileResponse editMyProfile(@RequestBody UserRequest request, @NotNull HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      User user = userRepository.findById(userId).orElseThrow();
      user.setFullname(request.getFullname());
      user.setConfirmPass("WHATEVER!");
      User freshUser = userRepository.save(user);
      UserRequest userDTO = modelMapper.map(freshUser, UserRequest.class);
      EditMyProfileResponse response = new EditMyProfileResponse();
      response.setUserDTO(userDTO);
      return response;
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
