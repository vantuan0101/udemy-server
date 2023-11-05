package com.java.udemy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.udemy.models.User;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.security.UserDetailsImplement;

import jakarta.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

@Service
public class MyUserDetailsService implements UserDetailsService {
    public static final String USERID = "USER_ID";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new UserDetailsImplement(user);
    }

    /**
     * Just return the user_id saved in Redis Store
     *
     * @param session session
     * @return userId
     */
    public static Integer getSessionUserId(@NotNull HttpSession session) {
        return (Integer) session.getAttribute(USERID);
    }
}
