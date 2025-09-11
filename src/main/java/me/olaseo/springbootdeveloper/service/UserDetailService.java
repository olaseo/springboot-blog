package me.olaseo.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.olaseo.springbootdeveloper.domain.User;
import me.olaseo.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

// Interface which gets User's info. provided by Spring Security
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // Method get User's information by username(or email)
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
