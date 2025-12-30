package com.moviebookingapp.user_module.controllers;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.dtos.UserLoginDTO;
import com.moviebookingapp.user_module.dtos.UserResetDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserController {

    ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO);

    String loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO);

    ResponseEntity<String> forgotPassword(@PathVariable String loginInput);
    ResponseEntity<String> forgotPasswordVerify(@PathVariable String loginInput, @RequestBody UserResetDTO userResetDTO);

    ResponseEntity<List<UserDTO>> getAllUsers();
    ResponseEntity<String> deleteUser(@RequestParam String loginID);
}
