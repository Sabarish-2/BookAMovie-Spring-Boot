package com.moviebookingapp.user_module.services;

import com.moviebookingapp.user_module.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    String loginUser(String loginInput, String password);
    UserDTO forgotPassword(String loginInput);
    UserDTO forgotPasswordCheck(String loginInput, String token);
    UserDTO retrieveUserByID(String loginID);
    List<UserDTO> retrieveAllUsers();
    void deleteUser(String loginID);
}
