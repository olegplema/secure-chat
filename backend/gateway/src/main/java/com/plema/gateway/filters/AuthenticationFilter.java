package com.plema.gateway.filters;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (config.isSecured.test(exchange.getRequest())) {
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeader == null || !exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is unauthorized");
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

        private static final List<String> openApiEndpoints = List.of(
                "/auth/register",
                "/auth/refresh",
                "/eureka");

        public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

}
