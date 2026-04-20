package com.example.APIGateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
public class JwtGatewayFilter
        extends AbstractGatewayFilterFactory<JwtGatewayFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();

            if (path.contains("/users/login") ||
            path.contains("/users/create")){
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            if (authHeader == null ||
            !authHeader.startsWith("Bearer ")){
                throw new RuntimeException("Missing JWT");
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)){
                throw new RuntimeException("Invalid JWT");
            }

            return chain.filter(exchange);


        });
    }

    public static class Config{

    }

}
