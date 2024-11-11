// package com.timescape.authentication_service.service;

// import java.util.Date;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.ldap.core.LdapTemplate;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.stereotype.Service;

// import com.timescape.authentication_service.dto.LoginRequest;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

// @Service
// public class AuthService {

//     @Autowired
//     private LdapTemplate ldapTemplate;

//     @Value("${jwt.secret}")
//     private String jwtSecret;

//     @Value("${jwt.expiration}")
//     private long jwtExpiration;

//     public String authenticate(LoginRequest loginRequest) {
//         if (isValidUser(loginRequest.getUsername(), loginRequest.getPassword())) {
//             return generateJwtToken(loginRequest.getUsername());
//         } else {
//             throw new BadCredentialsException("Invalid credentials");
//         }
//     }

//     private boolean isValidUser(String username, String password) {
//         return ldapTemplate.authenticate("", "(uid=" + username + ")", password);
//     }

//     private String generateJwtToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
//                 .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                 .compact();
//     }
// }

