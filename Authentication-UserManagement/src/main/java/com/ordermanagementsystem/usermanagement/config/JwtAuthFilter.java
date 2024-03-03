package com.ordermanagementsystem.usermanagement.config;

import com.ordermanagementsystem.usermanagement.jwtservice.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String autHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;



        // check if the auth header is empty or doesn't start with bearer will return and terminate the process
    if (autHeader == null || !autHeader.startsWith("Bearer")){


        filterChain.doFilter(request,response);
        return;
    }

        jwt = autHeader.substring(7);

        try{
            jwtService.isTokenExpired(jwt);
        }catch(ExpiredJwtException e){
            System.out.println("token expired for id : " + e.getClaims().getId());

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Expired Token");

            return;
        }
        catch (MalformedJwtException e){
            System.out.println("Token is not in proper format");

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Invalid Token");

            return;
        } catch (Exception e){
            System.out.println("Token is Invalid");

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Invalid Token");

            return;
        }

    // extract the jwt token


    userEmail = jwtService.extractUserEmail(jwt);

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if (jwtService.isTokenValid(jwt, userDetails)){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
    filterChain.doFilter(request,response);
    }
}
