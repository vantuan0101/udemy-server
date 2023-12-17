package com.java.udemy.controllers;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import com.java.udemy.request.UserRequest;
import com.java.udemy.response.EditMyProfileResponse;
import com.java.udemy.response.GetUserByIdResponse;
import com.java.udemy.service.abstractions.IUserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {

  @Autowired
  private IUserService userService;

  @GetMapping(path = "/me")
  public GetUserByIdResponse getUserById(@NotNull HttpSession session) {
    try {
      Integer userId = userService.getSessionUserId(session);
      UserRequest userDTO = userService.findUserById(userId);
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
      UserRequest userDTO = userService.updateProfileById(request, userId);
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
