package com.moviebookingapp.tickets_module.security;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
class UserDetailsServiceConfig implements UserDetailsService {

    @Override
    public @Nullable UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return null;
    }
}