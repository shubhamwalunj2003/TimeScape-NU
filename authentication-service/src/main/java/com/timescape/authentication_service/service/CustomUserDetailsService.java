package com.timescape.authentication_service.service;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person"))
              .and(new EqualsFilter("uid", username));
        List<UserDetails> users = ldapTemplate.search("ou=mathematicians,dc=example,dc=com", filter.encode(), new UserDetailsMapper());
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return users.get(0);
    }

    // Mapper to extract user information from LDAP
    private static class UserDetailsMapper implements AttributesMapper<UserDetails> {
        @Override
        public UserDetails mapFromAttributes(Attributes attributes) throws NamingException {
            String username = (String) attributes.get("uid").get();
            String password = "password"; // Replace with real LDAP password logic if needed
            return User.withUsername(username)
                    .password(password)
                    .roles("USER")
                    .build();
        }
    }
}

