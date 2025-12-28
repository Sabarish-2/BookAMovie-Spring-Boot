package com.moviebookingapp.user_module.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResetDTO {

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String token;

    @NotBlank(message = "{com.moviebookingapp.user_module.dtos.empty}")
    private String password;
}
