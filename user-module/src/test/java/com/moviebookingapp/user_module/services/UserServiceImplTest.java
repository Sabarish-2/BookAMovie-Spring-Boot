package com.moviebookingapp.user_module.services;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.entities.User;
import com.moviebookingapp.user_module.enums.UserRole;
import com.moviebookingapp.user_module.exception.UserNotFoundException;
import com.moviebookingapp.user_module.mappers.UserMapper;
import com.moviebookingapp.user_module.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private JWTUtil jwtUtil;
//
//    @Mock
//    private Authentication authentication;
//
//    @Mock
//    private UserDetails userDetails;

    @InjectMocks
    private UserServiceImpl userService;

    private final String firstName = "John";
    private final String lastName = "Doe";
    private final String loginID = "john123";
    private final String emailID = "john@example.com";
    private final String password = "password";
    private final long contactNumber = 9999999999L;

    private final UserDTO userDTO = new UserDTO(firstName, lastName, loginID, emailID, password, contactNumber,
            UserRole.CUSTOMER);
    private final User user = new User(loginID, firstName, lastName, emailID, password, contactNumber,
            UserRole.CUSTOMER, null, null);

//    @Test
//    @DisplayName("CreateUser-Positive")
//    void createUser_positive() {
//        User savedUser = new User(loginID, firstName, lastName, emailID, "encoded", contactNumber, UserRole.CUSTOMER,
//                null, null);
//        UserDTO savedDTO = new UserDTO(firstName, lastName, loginID, emailID, "encoded", contactNumber,
//                UserRole.CUSTOMER);
//
//        when(mapper.map(userDTO)).thenReturn(new User(loginID, firstName, lastName, emailID, password, contactNumber,
//                UserRole.CUSTOMER, null, null));
//        when(passwordEncoder.encode(password)).thenReturn("encoded");
//        when(userRepository.findByEmailIDOrLoginID(emailID, loginID)).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//        when(mapper.map(savedUser)).thenReturn(savedDTO);
//
//        UserDTO actual = userService.createUser(userDTO);
//
//        assertEquals(savedDTO, actual);
//    }
//
//    @Test
//    @DisplayName("CreateUser-Negative-UserAlreadyExists")
//    void createUser_negative_userAlreadyExists() {
//        when(mapper.map(new UserDTO())).thenReturn(user);
//        when(userRepository.findByEmailIDOrLoginID(emailID, loginID)).thenReturn(Optional.of(user));
//
//        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDTO));
//    }

//    @Test
//    @DisplayName("LoginUser-Positive")
//    void loginUser_positive() {
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getUsername()).thenReturn(loginID);
//        when(userDetails.getAuthorities())
//                .thenReturn(AuthorityUtils.createAuthorityList("ROLE_USER"));
//        when(jwtUtil.generateToken(eq(loginID), eq("ROLE_USER"))).thenReturn("token123");
//
//        String token = userService.loginUser(loginID, password);
//
//        assertEquals("token123", token);
//    }

    @Test
    @DisplayName("ForgotPassword-Positive")
    void forgotPassword_positive() {
        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.of(user));
        when(mapper.map(user)).thenReturn(userDTO);

        UserDTO actual = userService.forgotPassword(loginID);

        assertEquals(userDTO, actual);
    }

    @Test
    @DisplayName("ForgotPassword-Negative-UserNotFound")
    void forgotPassword_negative_userNotFound() {
        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.forgotPassword(loginID));
    }

    @Test
    @DisplayName("ForgotPasswordCheck-Positive")
    void forgotPasswordCheck_positive() {
        User updatedUser = new User(loginID, firstName, lastName, emailID, "encodedNew", contactNumber,
                UserRole.CUSTOMER, null, null);
        UserDTO updatedDTO = new UserDTO(firstName, lastName, loginID, emailID, "encodedNew", contactNumber,
                UserRole.CUSTOMER);

        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.of(user));
        when(mapper.map(user)).thenReturn(userDTO);
        when(mapper.map(userDTO)).thenReturn(user);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNew");
        when(userRepository.save(user)).thenReturn(updatedUser);
        when(mapper.map(updatedUser)).thenReturn(updatedDTO);

        UserDTO actual = userService.forgotPasswordCheck(loginID, "newPass");

        assertEquals(updatedDTO, actual);
    }

    @Test
    @DisplayName("ForgotPasswordCheck-Negative-UserNotFound")
    void forgotPasswordCheck_negative_userNotFound() {
        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.forgotPasswordCheck(loginID, "newPass"));
    }

    @Test
    @DisplayName("RetrieveUserByID-Positive")
    void retrieveUserByID_positive() {
        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.of(user));
        when(mapper.map(user)).thenReturn(userDTO);

        UserDTO actual = userService.retrieveUserByID(loginID);

        assertEquals(userDTO, actual);
    }

    @Test
    @DisplayName("RetrieveUserByID-Negative-UserNotFound")
    void retrieveUserByID_negative_userNotFound() {
        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.retrieveUserByID(loginID));
    }

    @Test
    @DisplayName("RetrieveAllUsers-Positive")
    void retrieveAllUsers_positive() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(mapper.map(user)).thenReturn(userDTO);

        List<UserDTO> actual = userService.retrieveAllUsers();

        assertEquals(List.of(userDTO), actual);
    }

    @Test
    @DisplayName("RetrieveAllUsers-Negative-UserNotFound")
    void retrieveAllUsers_negative_userNotFound() {
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(UserNotFoundException.class, () -> userService.retrieveAllUsers());
    }

    @Test
    @DisplayName("DeleteUser-Positive")
    void deleteUser_positive() {
        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.of(user));

        userService.deleteUser(loginID);

        verify(userRepository).deleteById(loginID);
    }

    @Test
    @DisplayName("DeleteUser-Negative-UserNotFound")
    void deleteUser_negative_userNotFound() {
        when(userRepository.findByEmailIDOrLoginID(loginID, loginID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(loginID));
    }
}
