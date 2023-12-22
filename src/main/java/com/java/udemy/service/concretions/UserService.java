package com.java.udemy.service.concretions;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.udemy.config.security.UserDetailsImplement;
import com.java.udemy.models.User;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.UserRequest;
import com.java.udemy.service.abstractions.IUserService;

import jakarta.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Service
public class UserService implements IUserService, UserDetailsService {
    public static final String USERID = "USER_ID";
    private final ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public UserService() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new UserDetailsImplement(user);
    }

    @Override
    public Integer getSessionUserId(@NotNull HttpSession session) {
        return (Integer) session.getAttribute(USERID);
    }

    @Override
    public UserRequest findUserById(Integer userId) {
        UserRequest userDTO = userRepository.findUserDTObyId(userId).orElseThrow();
        return userDTO;
    }

    @Override
    public UserRequest updateProfileById(UserRequest request, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setFullname(request.getFullname());
        user.setConfirmPass("WHATEVER!");
        User freshUser = userRepository.save(user);
        UserRequest userDTO = modelMapper.map(freshUser, UserRequest.class);
        return userDTO;
    }
}
