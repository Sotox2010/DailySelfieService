package com.coursera.androidcapstone.dailyselfie.auth;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import com.coursera.androidcapstone.dailyselfie.repository.ClientRepository;



public class ClientDetailsServiceWrapper implements ClientDetailsService {

    private final ClientRepository clients;

    public ClientDetailsServiceWrapper(ClientRepository clients) {
        this.clients = clients;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        if (!clients.exists(clientId)) {
            throw new ClientRegistrationException("Client ID '" + clientId + "\' not found!");
        }

        return clients.findOne(clientId);
    }

}
