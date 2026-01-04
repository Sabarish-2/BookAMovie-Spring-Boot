package com.moviebookingapp.user_module.controllers;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.dtos.UserLoginDTO;
import com.moviebookingapp.user_module.dtos.UserResetDTO;
import com.moviebookingapp.user_module.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/login")
    public String loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.loginUser(userLoginDTO.getLoginID(), userLoginDTO.getPassword());
    }

    @Override
    @GetMapping("/{loginInput}/forgot")
    public ResponseEntity<UserDTO> forgotPassword(String loginInput) {
        return ResponseEntity.ok(userService.forgotPassword(loginInput));
    }

    @Override
    @PostMapping("/{loginInput}/forgot")
    public ResponseEntity<UserDTO> forgotPasswordVerify(String loginInput, UserResetDTO userResetDTO) {
        return ResponseEntity.ok(userService.forgotPasswordCheck(loginInput, userResetDTO.getPassword()));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.retrieveAllUsers());
    }

    @Override
    @PreAuthorize("hasRole(\"ADMIN\")")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(String loginID) {
        userService.deleteUser(loginID);
        return ResponseEntity.ok("User with ID: " + loginID + " Removed Successfully");
    }

}
