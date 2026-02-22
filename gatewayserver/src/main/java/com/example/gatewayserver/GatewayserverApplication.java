package com.example.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }
    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/uniflow/identity/**")
                        .filters( f -> f.rewritePath("/uniflow/identity/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://IDENTITY"))
                .route(p -> p
                        .path("/uniflow/profile/**")
                        .filters( f -> f.rewritePath("/uniflow/profile/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://PROFILE"))
                .route(p -> p
                        .path("/uniflow/academic/**")
                        .filters( f -> f.rewritePath("/uniflow/academic/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ACADEMY"))
                .route(p -> p.path("/uniflow/enroll/**")
                .filters( f -> f.rewritePath("/uniflow/enroll/(?<segment>.*)","/${segment}")
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                .uri("lb://ENROLL")).build();

    }
}
