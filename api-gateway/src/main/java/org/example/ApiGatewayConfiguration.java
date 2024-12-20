package org.example;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/books/**").uri("lb://books"))
                .route(p -> p.path("/subscriptions/**").uri("lb://subscriptions"))
                .route(p -> p.path("/books-feign/**").uri("lb://books"))
                .build();
    }
}
