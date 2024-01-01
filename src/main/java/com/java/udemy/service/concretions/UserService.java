package com.java.udemy.service.concretions;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    public static final String USER_EMAIL = "USER_EMAIL";
    
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

        // Lưu USER_EMAIL vào session
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        session.setAttribute(USER_EMAIL, user.getEmail());
        
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
