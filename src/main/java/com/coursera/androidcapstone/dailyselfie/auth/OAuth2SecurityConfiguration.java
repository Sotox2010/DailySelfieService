package com.coursera.androidcapstone.dailyselfie.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.coursera.androidcapstone.dailyselfie.repository.ClientRepository;
import com.coursera.androidcapstone.dailyselfie.repository.DoctorRepository;
import com.coursera.androidcapstone.dailyselfie.repository.PatientRepository;
import com.coursera.androidcapstone.dailyselfie.repository.UserRepository;

/**
 *	Configure this web application to use OAuth 2.0.
 *
 * 	The resource server is located at "/video", and can be accessed only by retrieving a token from "/oauth/token"
 *  using the Password Grant Flow as specified by OAuth 2.0.
 *
 *  Most of this code can be reused in other applications. The key methods that would definitely need to
 *  be changed are:
 *
 *  ResourceServer.configure(...) - update this method to apply the appropriate 
 *  set of scope requirements on client requests
 *
 *  OAuth2Config constructor - update this constructor to create a "real" (not hard-coded) UserDetailsService
 *  and ClientDetailsService for authentication. The current implementation should never be used in any
 *  type of production environment as these hard-coded credentials are highly insecure.
 *
 *  OAuth2SecurityConfiguration.containerCustomizer(...) - update this method to use a real keystore
 *  and certificate signed by a CA. This current version is highly insecure.
 *
 */
@Configuration
public class OAuth2SecurityConfiguration {

    public static final String ROLE_USER = "USER";

    @Configuration
    @EnableWebSecurity
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        protected void registerAuthentication(
                final AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();

            http.authorizeRequests()
                .antMatchers("/oauth/token").anonymous();

            http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/photo/**")
                .access("#oauth2.hasScope('read') and hasRole('USER')");

            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/photo/**")
                .access("#oauth2.hasScope('write') and hasRole('USER')");
            /*
            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/doctor/**")
                .access("#oauth2.hasScope('write') and hasRole('DOCTOR')");

            http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/patient/**")
                .access("#oauth2.hasScope('read') and hasAnyRole('PATIENT', 'DOCTOR')");

            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/patient/**")
                .access("#oauth2.hasScope('write') and hasAnyRole('PATIENT', 'DOCTOR')");

            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/medicine/**")
                .access("#oauth2.hasScope('write') and hasRole('DOCTOR')");

            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/check_in/**")
                .access("#oauth2.hasScope('write') and hasRole('PATIENT')");*/
        }

    }

    @Configuration
    @EnableAuthorizationServer
    @Order(Ordered.LOWEST_PRECEDENCE - 100)
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

        // Delegate the processing of Authentication requests to the framework
        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserRepository users;

        @Autowired
        private ClientRepository clients;

        @Autowired
        private DataSource dataSource;

        private ClientAndUserDetailsService combinedService;

        @Autowired
        public OAuth2Config() {
            System.out.println("OAuth2Config() Constructor.");
        }

        @Bean
        public ClientDetailsService clientDetailsService() throws Exception {
            /*if(combinedService == null)
                combinedService = new ClientAndUserDetailService(doctorRepo, patientRepo, clientRepo);*/
            return combinedService;
        }

        @Bean
        public UserDetailsService userDetailsService() {
            UserDetailsService users = new UserDetailsServiceWrapper(this.users);
            ClientDetailsService clients = new ClientDetailsServiceWrapper(this.clients);

            combinedService = new ClientAndUserDetailsService(clients, users);

            return combinedService;
        }

        /*@Bean
        public ClientAndUserDetailsService getClientAndUserDetailsService() {
            DoctorAndPatientDetailsService users_ =
                    new DoctorAndPatientDetailsService(doctors, patients);

            ClientDetailsServiceWrapper clients_ =
                    new ClientDetailsServiceWrapper(clients);

            combinedService = new ClientAndUserDetailsService(users_, clients_);

            return combinedService;
        }*/

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
            endpoints.tokenStore(new JdbcTokenStore(dataSource));
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService());
        }

    }

}
