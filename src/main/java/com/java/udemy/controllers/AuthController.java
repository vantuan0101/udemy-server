package com.java.udemy.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.custom.LoginResponse;
import com.java.udemy.custom.GenericResponse;
import com.java.udemy.dto.LoginRequest;
import com.java.udemy.models.User;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.security.JwtUtils;
import com.java.udemy.security.UserDetailsImplement;
import com.java.udemy.service.MyUserDetailsService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<GenericResponse> addNewUser(@RequestBody @Valid User user) {
        if (!user.getPassword().equals(user.getConfirmPass()))
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(GenericResponse.fail("Passwords don't match"));

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse("Registered! Welcome"));
        } catch (Exception ex) {
            if (ex instanceof DataIntegrityViolationException) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(GenericResponse.fail("Account already exists"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GenericResponse.fail(ex.getMessage()));
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession) {
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

            httpSession.setAttribute(MyUserDetailsService.USERID, userDetails.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponse(
                            "Login Successfully!",
                            jwt,
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponse.fail(e.getMessage()));

        }
    }

}
