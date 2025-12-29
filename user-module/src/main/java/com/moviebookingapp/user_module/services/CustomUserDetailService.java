package com.moviebookingapp.user_module.services;

import com.moviebookingapp.user_module.entities.User;
import com.moviebookingapp.user_module.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String loginInput) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIDOrLoginID(loginInput, loginInput).orElseThrow(()
                -> new UsernameNotFoundException("User with ID: " + loginInput + " Not Found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLoginID())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getUserRole())
                // Might assign ROLE_ twice like ROLE_ROLE_ADMIN -> Check once!
                .build();
    }
}
