package com.timescape.authentication_service.config;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper; 
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:8389");
        contextSource.setBase("ou=people,dc=example,dc=com");
        contextSource.setUserDn("uid=admin,ou=system");
        contextSource.setPassword("admin");
        return contextSource;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()
            .userDnPatterns("uid={0},ou=people")
            .contextSource(ldapContextSource())
            .userDetailsContextMapper(new PersonContextMapper());
    }

    // Fixed PersonContextMapper class
    private static class PersonContextMapper implements UserDetailsContextMapper {

        @Override
        public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
                                              Collection<? extends GrantedAuthority> authorities) {
            String password = ctx.getStringAttribute("userPassword");
            return User.withUsername(username)
                    .password("{noop}" + password)
                    .authorities(authorities)
                    .build();
        }

        @Override
        public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
            // No implementation needed
        }
    }
}
