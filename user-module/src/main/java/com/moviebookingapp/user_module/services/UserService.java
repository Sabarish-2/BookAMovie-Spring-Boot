package com.moviebookingapp.user_module.services;

import com.moviebookingapp.user_module.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO loginUser(String loginInput, String password);
//    String forgotPassword(String loginInput);
//    String forgotPasswordCheck(String loginInput, String token);
    UserDTO retrieveUserByID(String loginID);
    List<UserDTO> retrieveAllUsers();
    void deleteUser(String loginID);
}
