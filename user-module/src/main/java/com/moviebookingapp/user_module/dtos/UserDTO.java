package com.moviebookingapp.user_module.dtos;


import com.moviebookingapp.user_module.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String loginID;
    @NotBlank
    private String emailID;
    @NotBlank
    private String password;
    @Min(value = 5999999999L, message = "Enter valid Mobile Number")
    private int contactNumber;
    private UserRole userRole;

}
