package com.moviebookingapp.user_module.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String loginID;

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String password;
}
