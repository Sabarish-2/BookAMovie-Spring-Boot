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

/**
 * Service implementation for managing user-related operations.
 * This class provides methods to create, authenticate, and manage users.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Repository for accessing user data.
     */
    private final UserRepository userRepository;

    /**
     * Mapper for converting between DTOs and entities.
     */
    private final UserMapper mapper;

    /**
     * Encoder for hashing user passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Manager for authenticating user credentials.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Utility for generating and validating JWT tokens.
     */
    private final JWTUtil jwtUtil;

    /**
     * Constructor for UserServiceImpl.
     *
     * @param userRepository        Repository for accessing user data.
     * @param mapper                Mapper for converting between DTOs and entities.
     * @param passwordEncoder       Encoder for hashing user passwords.
     * @param authenticationManager Manager for authenticating user credentials.
     * @param jwtUtil               Utility for generating and validating JWT
     *                              tokens.
     */
    public UserServiceImpl(UserRepository userRepository, UserMapper mapper, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Creates a new user in the system.
     *
     * @param userDTO Data transfer object containing user details.
     * @return The created user as a DTO.
     * @throws UserAlreadyExistsException if the user already exists.
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapper.map(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByEmailIDOrLoginID(userDTO.getEmailID(), userDTO.getLoginID()).isPresent()) {
            throw new UserAlreadyExistsException("User With Same Email or Login ID Already exists!!");
        }
        User createdUser = userRepository.save(user);
        return mapper.map(createdUser);
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginInput The user's login ID or email.
     * @param password   The user's password.
     * @return A JWT token for the authenticated user.
     */
    @Override
    public String loginUser(String loginInput, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput, password));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtUtil.generateToken(Objects.requireNonNull(userDetails).getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority());
    }

    /**
     * Retrieves a user by their login ID or email for password recovery.
     *
     * @param loginInput The user's login ID or email.
     * @return The user as a DTO.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public UserDTO forgotPassword(String loginInput) {
        return retrieveUserByID(loginInput);
    }

    /**
     * Updates the password of a user.
     *
     * @param loginInput The user's login ID or email.
     * @param password   The new password.
     * @return The updated user as a DTO.
     */
    @Override
    public UserDTO forgotPasswordCheck(String loginInput, String password) {
        UserDTO userDTO = retrieveUserByID(loginInput);
        User user = mapper.map(userDTO);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return mapper.map(user);
    }

    /**
     * Retrieves a user by their login ID or email.
     *
     * @param loginID The user's login ID or email.
     * @return The user as a DTO.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public UserDTO retrieveUserByID(String loginID) {
        User user = userRepository.findByEmailIDOrLoginID(loginID, loginID)
                .orElseThrow(() -> new UserNotFoundException(loginID));
        return mapper.map(user);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return A list of all users as DTOs.
     * @throws UserNotFoundException if no users are found.
     */
    @Override
    public List<UserDTO> retrieveAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream().map(mapper::map).toList();
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }
        return users;
    }

    /**
     * Deletes a user from the system.
     *
     * @param loginID The user's login ID or email.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public void deleteUser(String loginID) {
        userRepository.findByEmailIDOrLoginID(loginID, loginID).orElseThrow(() -> new UserNotFoundException(loginID));
        userRepository.deleteById(loginID);
    }

}
