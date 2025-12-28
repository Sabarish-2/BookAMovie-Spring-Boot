package com.moviebookingapp.user_module.dtos;


import com.moviebookingapp.user_module.enums.UserRole;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String firstName;

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String lastName;

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String loginID;

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String emailID;

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String password;

    @Min(value = 5999999999L, message = "{com.moviebookingapp.user_module.dtos.contactNumber.invalid}")
    private long contactNumber;

    private UserRole userRole;

}
