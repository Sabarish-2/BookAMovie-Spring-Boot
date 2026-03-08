package com.moviebookingapp.user_module.controllers;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.dtos.UserLoginDTO;
import com.moviebookingapp.user_module.dtos.UserResetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ApiResponse(responseCode = "401", description = "Authentication Error")
@ApiResponse(responseCode = "503", description = "Required Tickets Microservice Unreachable")
@ApiResponse(responseCode = "500", description = "Unexpected Error Internally")
public interface UserController {


    @Operation(summary = "Register A user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User Registered Successfully"),
            @ApiResponse(responseCode = "400", description = "Validation Error in User Details Provided"),
            @ApiResponse(responseCode = "409", description = "User Already Exists")
    })
    ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO);

    @Operation(summary = "Login A user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Logged In Successfully"),
            @ApiResponse(responseCode = "400", description = "Validation Error in User Details Provided"),
            @ApiResponse(responseCode = "404", description = "User Does Not Exists")
    })
    String loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO);

    @Operation(summary = "Reset Password for User Verification")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Receive A Code Successfully"),
            @ApiResponse(responseCode = "400", description = "Validation Error in User Details Provided"),
            @ApiResponse(responseCode = "404", description = "User Does Not Exists")
    })
    ResponseEntity<UserDTO> forgotPassword(@PathVariable String loginInput);

    @Operation(summary = "Reset Password After Verification")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Resets Their Password Successfully"),
            @ApiResponse(responseCode = "400", description = "Validation Error in User Details Provided"),
            @ApiResponse(responseCode = "404", description = "User Does Not Exists")
    })
    ResponseEntity<UserDTO> forgotPasswordVerify(@PathVariable String loginInput, @RequestBody UserResetDTO userResetDTO);

    @Operation(summary = "Retrieve All Users", method = "User Controller")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Users Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "No Users Retrieved")
    })
    ResponseEntity<List<UserDTO>> getAllUsers();

    @Operation(summary = "Delete An Existing User")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Deleted Successfully"),
            @ApiResponse(responseCode = "404", description = "User Not Found")
    })
    ResponseEntity<String> deleteUser(@RequestParam String loginID);
}
