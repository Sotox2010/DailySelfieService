package com.coursera.androidcapstone.dailyselfie.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.coursera.androidcapstone.dailyselfie.repository.UserRepository;

public class UserDetailsServiceWrapper implements UserDetailsService {

    private UserRepository users;

    public UserDetailsServiceWrapper(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User E-mail '" + username + "\' not found!");
        }

        return user;
    }

}
