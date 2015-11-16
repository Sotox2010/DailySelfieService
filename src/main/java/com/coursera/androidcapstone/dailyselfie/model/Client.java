package com.coursera.androidcapstone.dailyselfie.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

@Entity
public class Client implements ClientDetails {

    private static final long serialVersionUID = 3L;

    public static final String SPLIT_PATTERN = "\\s*,\\s*";

    @Id
    @Column(name="id")
    private String id;

    @Column(name="client_secret")
    private String client_secret;

    @Column(name="resources")
    private String resources;

    @Column(name="grant_types")
    private String grant_types;

    @Column(name="scopes")
    private String scopes;

    @Column(name="authorities")
    private String authorities;


    @Override
    public String getClientId() {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public Set<String> getResourceIds() {
        // TODO Auto-generated method stub
        return new HashSet<String>(Arrays.asList(resources.split(SPLIT_PATTERN)));
    }

    @Override
    public boolean isSecretRequired() {
        // TODO Auto-generated method stub
        return client_secret != null;
    }

    @Override
    public String getClientSecret() {
        // TODO Auto-generated method stub
        return client_secret;
    }

    @Override
    public boolean isScoped() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Set<String> getScope() {
        // TODO Auto-generated method stub
        return new HashSet<String>(Arrays.asList(scopes.split(SPLIT_PATTERN)));
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        // TODO Auto-generated method stub
        return new HashSet<String>(Arrays.asList(grant_types.split(SPLIT_PATTERN)));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return AuthorityUtils.createAuthorityList(authorities.split(SPLIT_PATTERN));
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        // TODO Auto-generated method stub
        return -1;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        // TODO Auto-generated method stub
        return -1;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        // TODO Auto-generated method stub
        return null;
    }

}
