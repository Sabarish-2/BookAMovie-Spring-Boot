package com.moviebookingapp.user_module.entities;

import com.moviebookingapp.user_module.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String loginID;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Column(unique = true)
    private String emailID;

    @NotBlank
    private String password;

    private long contactNumber;

//    @NotBlank
    private UserRole userRole;

    private String resetToken;
    private LocalDateTime resetTokenExpiry;
}
