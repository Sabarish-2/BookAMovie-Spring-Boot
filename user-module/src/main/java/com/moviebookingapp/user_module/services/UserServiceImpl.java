package com.moviebookingapp.user_module.services;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.entities.User;
import com.moviebookingapp.user_module.mappers.UserMapper;
import com.moviebookingapp.user_module.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        TODO: Already exists user chk n exception
        User createdUser = userRepository.save(user);
        return mapper.map(createdUser);
    }

    @Override
//    TODO
    public UserDTO loginUser(String loginInput, String password) {
//        TODO: Not Found Chk n Exception
        return null;
    }

    @Override
    public UserDTO retrieveUserByID(String loginID) {
//        TODO: Not Found Chk n Exception
        User user = userRepository.findById(loginID).orElseThrow();
        return mapper.map(user);
    }

    @Override
    public List<UserDTO> retrieveAllUsers() {
//        TODO: Not Found Chk n Exception
        return userRepository.findAll().stream().map(mapper::map).toList();
    }

    @Override
    public void deleteUser(String loginID) {
//        TODO: Not Found Chk n Exception
        userRepository.deleteById(loginID);
    }

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIDOrLoginID(username, username).orElseThrow(() -> new UsernameNotFoundException("User with ID: " + username + " Not Found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLoginID())
                .password(user.getPassword())
                .authorities(String.valueOf(user.getUserRole()))
                .build();
    }
}
