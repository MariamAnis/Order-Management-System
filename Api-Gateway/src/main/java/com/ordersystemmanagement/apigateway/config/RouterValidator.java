package com.ordersystemmanagement.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

//    private static String authOpenEndPoint= "/api/v1/auth/**";
    public static final List<String> openApiEndpoints = List.of(
          "/api/v1/auth/register","/api/v1/auth/authenticate", "/api/v1/auth/user"
    );

    public static final List<String> securedApiEndpoints = List.of(
            "/test",
            "/products",
            "/selling",
            "/buying"
    );
    public static final List<String> managerEndPoints = List.of(
        "/categories"
    );

    public static final List<String> userEndPoints = List.of(
            "/orders",
            "/selling/submit-order"
    );
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isSecuredAuthorization =
            request -> managerEndPoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isManagerEndPoints =
            request -> securedApiEndpoints
                    .stream()
                    .allMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isUserEndPoints =
            request -> userEndPoints
                    .stream()
                    .allMatch(uri -> request.getURI().getPath().contains(uri));



}