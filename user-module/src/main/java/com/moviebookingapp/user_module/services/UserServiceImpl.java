package com.moviebookingapp.user_module.services;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.entities.User;
import com.moviebookingapp.user_module.exception.UserAlreadyExistsException;
import com.moviebookingapp.user_module.exception.UserNotFoundException;
import com.moviebookingapp.user_module.mappers.UserMapper;
import com.moviebookingapp.user_module.repositories.UserRepository;
import com.moviebookingapp.user_module.security.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    public UserServiceImpl(UserRepository userRepository, UserMapper mapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.findByEmailIDOrLoginID(userDTO.getEmailID(), userDTO.getLoginID()).orElseThrow(
                () -> new UserAlreadyExistsException("User With Same Email or Login ID Already exists!!"));
        User createdUser = userRepository.save(user);
        return mapper.map(createdUser);
    }

    @Override
    public String loginUser(String loginInput, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput, password)
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtUtil.generateToken(Objects.requireNonNull(userDetails).getUsername(), userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Override
    public UserDTO retrieveUserByID(String loginID) {
        User user = userRepository.findByEmailIDOrLoginID(loginID, loginID).orElseThrow(() -> new UserNotFoundException(loginID));
        return mapper.map(user);
    }

    @Override
    public List<UserDTO> retrieveAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream().map(mapper::map).toList();
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }
        return users;
    }

    @Override
    public void deleteUser(String loginID) {
        userRepository.findByEmailIDOrLoginID(loginID, loginID).orElseThrow(() -> new UserNotFoundException(loginID));
        userRepository.deleteById(loginID);
    }

}
