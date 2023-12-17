package com.java.udemy.service.abstractions;

import org.springframework.security.core.userdetails.UserDetails;

import com.java.udemy.request.UserRequest;

import jakarta.servlet.http.HttpSession;

public interface IUserService {
  Integer getSessionUserId(HttpSession session);

  UserDetails loadUserByUsername(String username);

  UserRequest findUserById(Integer userId);

  UserRequest updateProfileById(UserRequest request, Integer userId);
}
