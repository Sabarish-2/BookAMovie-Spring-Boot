package com.moviebookingapp.user_module.controllers;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.dtos.UserLoginDTO;
import com.moviebookingapp.user_module.dtos.UserResetDTO;
import com.moviebookingapp.user_module.enums.UserRole;
import com.moviebookingapp.user_module.exception.UserAlreadyExistsException;
import com.moviebookingapp.user_module.exception.UserNotFoundException;
import com.moviebookingapp.user_module.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {

    @InjectMocks
    private UserControllerImpl userController;

    @Mock
    private UserService userService;

    private final UserDTO userDTO = new UserDTO("John", "Doe", "john123", "john@example.com", "password", 9999999999L,
            UserRole.CUSTOMER);

    @Test
    @DisplayName("RegisterUser-Positive")
    void registerUser_positive() {
        when(userService.createUser(userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.registerUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    @DisplayName("RegisterUser-Negative-UserAlreadyExists")
    void registerUser_negative_userAlreadyExists() {
        when(userService.createUser(userDTO)).thenThrow(new UserAlreadyExistsException("exists"));

        assertThrows(UserAlreadyExistsException.class, () -> userController.registerUser(userDTO));
    }

    @Test
    @DisplayName("LoginUser-Positive")
    void loginUser_positive() {
        UserLoginDTO loginDTO = new UserLoginDTO("john123", "password");
        when(userService.loginUser(loginDTO.getLoginID(), loginDTO.getPassword())).thenReturn("token123");

        String token = userController.loginUser(loginDTO);

        assertEquals("token123", token);
    }

    @Test
    @DisplayName("LoginUser-Negative-UserNotFound")
    void loginUser_negative_userNotFound() {
        UserLoginDTO loginDTO = new UserLoginDTO("john123", "password");
        when(userService.loginUser(loginDTO.getLoginID(), loginDTO.getPassword()))
                .thenThrow(new UserNotFoundException("john123"));

        assertThrows(UserNotFoundException.class, () -> userController.loginUser(loginDTO));
    }

    @Test
    @DisplayName("ForgotPassword-Positive")
    void forgotPassword_positive() {
        when(userService.forgotPassword("john123")).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.forgotPassword("john123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    @DisplayName("ForgotPassword-Negative-UserNotFound")
    void forgotPassword_negative_userNotFound() {
        when(userService.forgotPassword("john123")).thenThrow(new UserNotFoundException("john123"));

        assertThrows(UserNotFoundException.class, () -> userController.forgotPassword("john123"));
    }

    @Test
    @DisplayName("ForgotPasswordVerify-Positive")
    void forgotPasswordVerify_positive() {
        UserResetDTO resetDTO = new UserResetDTO("token", "newPass");
        when(userService.forgotPasswordCheck("john123", resetDTO.getPassword())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.forgotPasswordVerify("john123", resetDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    @DisplayName("ForgotPasswordVerify-Negative-UserNotFound")
    void forgotPasswordVerify_negative_userNotFound() {
        UserResetDTO resetDTO = new UserResetDTO("token", "newPass");
        when(userService.forgotPasswordCheck("john123", resetDTO.getPassword()))
                .thenThrow(new UserNotFoundException("john123"));

        assertThrows(UserNotFoundException.class, () -> userController.forgotPasswordVerify("john123", resetDTO));
    }

    @Test
    @DisplayName("GetAllUsers-Positive")
    void getAllUsers_positive() {
        when(userService.retrieveAllUsers()).thenReturn(List.of(userDTO));

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(userDTO), response.getBody());
    }

    @Test
    @DisplayName("GetAllUsers-Negative-UserNotFound")
    void getAllUsers_negative_userNotFound() {
        when(userService.retrieveAllUsers()).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> userController.getAllUsers());
    }

    @Test
    @DisplayName("DeleteUser-Positive")
    void deleteUser_positive() {
        ResponseEntity<String> response = userController.deleteUser("john123");

        verify(userService).deleteUser("john123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User with ID: john123 Removed Successfully", response.getBody());
    }

    @Test
    @DisplayName("DeleteUser-Negative-UserNotFound")
    void deleteUser_negative_userNotFound() {
        doThrow(new UserNotFoundException("john123")).when(userService).deleteUser("john123");

        assertThrows(UserNotFoundException.class, () -> userController.deleteUser("john123"));
    }
}
