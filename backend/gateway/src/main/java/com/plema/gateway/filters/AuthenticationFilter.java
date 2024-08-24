package com.plema.gateway.filters;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.plema.gateway.dtos.TokenInfo;
import com.plema.gateway.grpc.interfaces.AuthClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private AuthClient authClient;

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

                TokenInfo tokenInfo = authClient.isTokenValid(authHeader);

                if (!tokenInfo.getIsTokenValid()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is unauthorized");
                }

                exchange = exchange.mutate()
                        .request(exchange.getRequest().mutate()
                                .header("X-User-ID", tokenInfo.getUserId()).build())
                        .build();
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
