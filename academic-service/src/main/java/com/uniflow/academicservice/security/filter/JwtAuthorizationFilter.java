package com.uniflow.academicservice.security.filter;

import com.uniflow.academicservice.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            processToken(request);
        } catch (Exception e) {
            logger.error("Failed to process JWT Token: " + e.getMessage());
        }
        logger.debug("Processing complete. Return back control to framework");
        filterChain.doFilter(request, response);
    }
    private void processToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        logger.debug("Authorization Header: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String jwtToken = authHeader.substring(7);

        if (!jwtService.isTokenValid(jwtToken)) {
            return;
        }
        String userName = jwtService.extractUsername(jwtToken);

        if (userName == null) {
            return;
        }
        logger.info("Username found in JWT: " + userName);
        String role = jwtService.extractRole(jwtToken);
        if (role == null) {
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return;
        }
        logger.info("Create authentication instance for " + userName);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
