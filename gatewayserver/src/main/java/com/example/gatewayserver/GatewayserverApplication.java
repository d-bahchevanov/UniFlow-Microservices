package com.example.gatewayserver;

import com.example.gatewayserver.security.filter.JwtGatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }
    @Bean
    public RouteLocator uniFlowRouteConfig(RouteLocatorBuilder routeLocatorBuilder, JwtGatewayFilter jwtGatewayFilter) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/uniflow/identity/**")
                        .filters( f -> f.filter(jwtGatewayFilter).rewritePath("/uniflow/identity/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://IDENTITY"))
                .route(p -> p
                        .path("/uniflow/profile/**")
                        .filters( f -> f.filter(jwtGatewayFilter).rewritePath("/uniflow/profile/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://PROFILE"))
                .route(p -> p
                        .path("/uniflow/academic/**")
                        .filters( f -> f.filter(jwtGatewayFilter).rewritePath("/uniflow/academic/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ACADEMIC"))
                .route(p -> p.path("/uniflow/enroll/**")
                .filters( f -> f.filter(jwtGatewayFilter).rewritePath("/uniflow/enroll/(?<segment>.*)","/${segment}")
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                .uri("lb://ENROLL")).build();

    }
    @Bean
    public GlobalFilter responseTimeFilter() {
        return (exchange, chain) -> {
            long start = System.currentTimeMillis();
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        long duration = System.currentTimeMillis() - start;
                        exchange.getResponse().getHeaders().add("X-Response-Time", duration + "ms");
                    }));
        };
    }
}
