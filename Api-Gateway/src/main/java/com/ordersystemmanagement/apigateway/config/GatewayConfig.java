package com.ordersystemmanagement.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {


//    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route( r -> r.path("/api/v1/auth/**")
                        .uri("lb://user-management"))
                .route( r -> r.path("/test/v1")
                        .uri("lb://user-management"))
                .route( r -> r.path("/products/**")
                        .uri("lb://inventory-service"))
                .route( r -> r.path("/categories/**")
                        .uri("lb://inventory-service"))
                .route( r -> r.path("/buying/**")
                        .uri("lb://purchasing-management-service"))
                .route( r -> r.path("/selling/**")
                        .uri("lb://purchasing-management-service"))
                .route( r -> r.path("/api/order")
                        .uri("lb://order-service"))
                .build();



    }

}