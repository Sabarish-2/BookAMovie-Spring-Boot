package com.moviebookingapp.user_module.controllers;

import com.moviebookingapp.user_module.configurations.JWTUtil;
import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.dtos.UserLoginDTO;
import com.moviebookingapp.user_module.dtos.UserResetDTO;
import com.moviebookingapp.user_module.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class UserControllerImpl implements UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtUtil(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/login")
    public String loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getLoginID(), userLoginDTO.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtUtil.generateToken(Objects.requireNonNull(userDetails).getUsername(), userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Override
    @GetMapping("/{loginInput}/forgot")
    public ResponseEntity<String> forgotPassword(String loginInput) {
        return null;
    }

    @Override
    @PostMapping("/{loginInput}/forgot")
    public ResponseEntity<String> forgotPasswordVerify(String loginInput, UserResetDTO userResetDTO) {
        return null;
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.retrieveAllUsers());
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(String loginID) {
        userService.deleteUser(loginID);
        return ResponseEntity.ok("User with ID: " + loginID + " Removed Successfully");
    }

}
