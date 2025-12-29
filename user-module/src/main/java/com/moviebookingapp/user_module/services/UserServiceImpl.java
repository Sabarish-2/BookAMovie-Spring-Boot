package com.moviebookingapp.user_module.services;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.entities.User;
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
//        TODO: Already exists user chk n exception
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

}
