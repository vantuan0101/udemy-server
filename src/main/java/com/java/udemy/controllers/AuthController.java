package com.java.udemy.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.config.Constants;
import com.java.udemy.config.security.JwtUtils;
import com.java.udemy.config.security.UserDetailsImplement;
import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.User;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.LoginRequest;
import com.java.udemy.response.GenericResponse;
import com.java.udemy.response.LoginResponse;
import com.java.udemy.service.concretions.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping(path = "/register")
    public GenericResponse addNewUser(@RequestBody @Valid User user) {
        try {
            if (!user.getPassword().equals(user.getConfirmPass()))
                throw new BadRequestException(Constants.MESSAGE_INVALID_MATCH_PASSWORD);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            GenericResponse response = new GenericResponse(Constants.MESSAGE_REGISTER_WELCOME);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession) {
        try {
            String password = loginRequest.getPassword();
            String email = loginRequest.getEmail();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            httpSession.setAttribute(UserService.USERID, userDetails.getId());
            LoginResponse response = new LoginResponse(
                    Constants.MESSAGE_LOGIN_SUCCESS,
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }

    }

    @PostMapping(path = "/logout")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> logout(HttpSession httpSession) {
        try {
            httpSession.invalidate();
            return ResponseEntity.ok().body("Logout Successfully");
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }

    }
}
