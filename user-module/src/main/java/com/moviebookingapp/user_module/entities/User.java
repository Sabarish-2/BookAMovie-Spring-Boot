package com.moviebookingapp.user_module.entities;

import com.moviebookingapp.user_module.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String emailID;

    @Column(nullable = false)
    private String password;

    @Min(5999999999L)
    @Column(nullable = false)
    private long contactNumber;

    @Column(nullable = false)
    private UserRole userRole;

    private String resetToken;
    private LocalDateTime resetTokenExpiry;
}
