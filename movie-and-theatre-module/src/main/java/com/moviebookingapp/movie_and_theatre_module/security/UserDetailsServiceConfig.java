package com.moviebookingapp.movie_and_theatre_module.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Configuration class for user details service.
 * This class implements the UserDetailsService interface to provide
 * user-specific data for authentication.
 */
@Component
class UserDetailsServiceConfig implements UserDetailsService {

    /**
     * Loads user-specific data by username.
     *
     * @param username The username of the user to load.
     * @return UserDetails containing user-specific data.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}