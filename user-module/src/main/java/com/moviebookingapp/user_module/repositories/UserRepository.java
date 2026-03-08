package com.moviebookingapp.user_module.repositories;

import com.moviebookingapp.user_module.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailIDOrLoginID(String emailID, String loginID);
}
