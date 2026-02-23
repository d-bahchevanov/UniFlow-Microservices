package com.uniflow.identity.security.filter;

import com.uniflow.identity.security.jwt.service.JwtService;
import com.uniflow.identity.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import  org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private HandlerExceptionResolver handlerExceptionResolver;
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            processToken(request);
        } catch (Exception e) {
            logger.error("Failed to process JWT Token: " + e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
        logger.debug("Processing complete. Return back control to framework");
        filterChain.doFilter(request, response);
    }
    private void processToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization Header: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("No Bearer Header, skip processing");
            return;
        }
        final String jwtToken = authHeader.substring(7);

        if (!jwtService.isTokenValid(jwtToken)) {
            logger.info("Token validity expired");
            return;
        }
        String userName = jwtService.extractUsername(jwtToken);

        if (userName == null) {
            logger.info("No username found in JWT Token");
            return;
        }

        logger.info("Username found in JWT: " + userName);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("Already logged in: " + userName);
            return;
        }
        logger.info("Create authentication instance for " + userName);
        UserDetails userDetails = userService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
