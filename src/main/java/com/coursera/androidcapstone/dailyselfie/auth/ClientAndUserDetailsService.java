/* 
 **
 ** Copyright 2014, Jules White
 **
 ** 
 */
package com.coursera.androidcapstone.dailyselfie.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

/**
 * A class that combines a UserDetailsService and ClientDetailsService
 * into a single object.
 */
public class ClientAndUserDetailsService implements UserDetailsService, ClientDetailsService {

    private final ClientDetailsService clients;

    private final UserDetailsService users;

    public ClientAndUserDetailsService(ClientDetailsService clients,
            UserDetailsService users) {
        this.users = users;
        this.clients = clients;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clients.loadClientByClientId(clientId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.loadUserByUsername(username);
    }

}
