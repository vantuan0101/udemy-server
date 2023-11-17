package com.java.udemy.service.abstractions;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.HttpSession;

public interface IUserService {
  Integer getSessionUserId(HttpSession session);

  UserDetails loadUserByUsername(String username);
}
