package com.moviebookingapp.user_module.entities;

import com.moviebookingapp.user_module.enums.UserRole;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String loginID;
    private String emailID;
    private String password;
    private int contactNumber;
    private UserRole userRole;
}
