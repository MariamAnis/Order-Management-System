package com.ordersystemmanagement.apigateway.config;

import com.ordersystemmanagement.apigateway.jwtservice.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GlobalPreFilter implements GlobalFilter {

    private final RouterValidator routerValidator;
    private final JwtService jwtService;

    final Logger logger =
            LoggerFactory.getLogger(GlobalPreFilter.class);

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {
        logger.info("im applied");


        if (!routerValidator.isSecured.test(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        String authHeader;
        try {
            authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));

        } catch (NullPointerException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            byte[] bytes = "Auth Header is empty".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));

//            return exchange.getResponse().setComplete();

        }

        authHeader = authHeader.substring(7);
        ArrayList<String> roles;
        try{
            jwtService.isTokenValid(authHeader);

            Claims claims = jwtService.extractAllClaims(authHeader);


             roles = (ArrayList<String>) claims.get("roles");

            System.out.println("Roles is "+roles.get(0));



        }catch (Exception e){
            logger.info(e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            byte[] bytes = "Auth Token is invalid".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }

        if (routerValidator.isUserEndPoints.test(exchange.getRequest())){
            if(roles.get(0).equals("ROLE_USER")){
                return chain.filter(exchange);
            }else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                byte[] bytes = "UnAuthorized access to Resource".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Flux.just(buffer));
            }
        }

        if (routerValidator.isManagerEndPoints.test(exchange.getRequest())){
            if(roles.get(0).equals("ROLE_MANAGER")){
                return chain.filter(exchange);
            }else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                byte[] bytes = "UnAuthorized access to Resource".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Flux.just(buffer));
            }
        }

        if (routerValidator.isSecuredAuthorization.test(exchange.getRequest())){

            if (exchange.getRequest().getPath().toString().equals("/buying") ||  exchange.getRequest().getPath().toString().equals("/selling") ){
                if(  roles.get(0).equals("ROLE_MANAGER") || roles.get(0).equals("ROLE_ADMIN")){
                    return chain.filter(exchange);
                }else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    byte[] bytes = "UnAuthorized access to Resource".getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    return exchange.getResponse().writeWith(Flux.just(buffer));
                }
            }
            if(exchange.getRequest().getMethod().equals(HttpMethod.GET)){
                return chain.filter(exchange);
            }

            if(  roles.get(0).equals("ROLE_MANAGER") || roles.get(0).equals("ROLE_ADMIN")){
                return chain.filter(exchange);
            }else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                byte[] bytes = "UnAuthorized access to Resource".getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Flux.just(buffer));
            }
        }

        return chain.filter(exchange);


    }



}

