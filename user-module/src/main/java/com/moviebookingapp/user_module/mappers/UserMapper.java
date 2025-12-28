package com.moviebookingapp.user_module.mappers;

import com.moviebookingapp.user_module.dtos.UserDTO;
import com.moviebookingapp.user_module.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

        UserDTO map(User user);
        User map(UserDTO userDto);
}
