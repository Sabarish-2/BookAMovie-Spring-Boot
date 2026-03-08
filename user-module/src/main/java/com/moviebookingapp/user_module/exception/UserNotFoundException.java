package com.moviebookingapp.user_module.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserNotFoundException extends CustomException {

    @Serial
    private static final long serialVersionUID = 4L;

    public UserNotFoundException() {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No Users Found!!");

    }

    public UserNotFoundException(String loginID) {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No User with Email or Login ID: " + loginID + " Found!");
    }
}
