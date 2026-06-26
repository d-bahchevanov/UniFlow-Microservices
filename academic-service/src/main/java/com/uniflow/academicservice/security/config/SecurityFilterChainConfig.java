package com.uniflow.academicservice.security.config;

import com.uniflow.academicservice.security.filter.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityFilterChainConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/faculty/create", "/faculty/delete/", "/admin/faculty/get/**",
                        "/specialization/create", "/specialization/delete","/specialization/validate", "/admin/specialization/get/**",
                        "/subject/create", "/subject/delete/", "/subject/validate").hasRole("ADMIN").anyRequest().authenticated()).csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}