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

/**
 * Controller implementation for managing user-related operations.
 * This class handles HTTP requests for user registration, login, and password
 * management.
 */
@RestController
public class UserControllerImpl implements UserController {

    /**
     * Service for handling user-related business logic.
     */
    private UserService userService;

    /**
     * Sets the user service.
     *
     * @param userService The user service to set.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param userDTO The user details to register.
     * @return A response entity containing the registered user.
     */
    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    /**
     * Logs in a user.
     *
     * @param userLoginDTO The login details of the user.
     * @return A token for the logged-in user.
     */
    @Override
    @PostMapping("/login")
    public String loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.loginUser(userLoginDTO.getLoginID(), userLoginDTO.getPassword());
    }

    /**
     * Handles forgot password requests.
     *
     * @param loginInput The login input (username or email) of the user.
     * @return A response entity containing the user details.
     */
    @Override
    @GetMapping("/{loginInput}/forgot")
    public ResponseEntity<UserDTO> forgotPassword(String loginInput) {
        return ResponseEntity.ok(userService.forgotPassword(loginInput));
    }

    /**
     * Verifies the forgot password request.
     *
     * @param loginInput   The login input (username or email) of the user.
     * @param userResetDTO The new password details.
     * @return A response entity containing the updated user details.
     */
    @Override
    @PostMapping("/{loginInput}/forgot")
    public ResponseEntity<UserDTO> forgotPasswordVerify(String loginInput, UserResetDTO userResetDTO) {
        return ResponseEntity.ok(userService.forgotPasswordCheck(loginInput, userResetDTO.getPassword()));
    }

    /**
     * Retrieves all users.
     *
     * @return A response entity containing the list of all users.
     */
    @Override
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.retrieveAllUsers());
    }

    /**
     * Deletes a user.
     *
     * @param loginID The login ID of the user to delete.
     * @return A response entity containing the deletion status.
     */
    @Override
    @PreAuthorize("hasRole(\"ADMIN\")")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(String loginID) {
        userService.deleteUser(loginID);
        return ResponseEntity.ok("User with ID: " + loginID + " Removed Successfully");
    }

}
