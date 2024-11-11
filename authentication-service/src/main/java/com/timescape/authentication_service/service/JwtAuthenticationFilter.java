// package com.timescape.authentication_service.service;

// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;

// public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//     private String jwtSecret = "mySecretKey";  // Replace with your jwt.secret property

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
//         String token = request.getHeader("Authorization");
//         if (token != null && token.startsWith("Bearer ")) {
//             String jwt = token.substring(7);
//             String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();

//             if (username != null) {
//                 SecurityContextHolder.getContext().setAuthentication(
//                     new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
//             }
//         }
//         chain.doFilter(request, response);
//     }
// }

