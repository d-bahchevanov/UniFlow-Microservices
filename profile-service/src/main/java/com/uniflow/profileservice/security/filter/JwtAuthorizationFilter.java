package com.uniflow.profileservice.security.filter;

import com.uniflow.profileservice.security.jwt.JwtService;
import com.uniflow.profileservice.service.ProfileService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final ProfileService profileService;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return;
        }
        logger.info("Create authentication instance for " + userName);
        UserDetails userDetails = profileService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
