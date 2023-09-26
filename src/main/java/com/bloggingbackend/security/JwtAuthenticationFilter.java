package com.bloggingbackend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. get token

        String requestToken =request.getHeader("Authorization");

        String username = null;
        String token =null;
        if(requestToken!=null && requestToken.startsWith("Bearer")){
            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                System.out.println("unable to get token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT token expired");
            }catch (MalformedJwtException e){
                System.out.println("Invalid Jwt Exception");
            }
        }else{
            System.out.println("JWT does not start with Bearer");
        }

        //once we get token we will validate

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails user = this.userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token,user)){
                //auth krna hai
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else{
                System.out.println("Invalid token");
            }
        }else{
            System.out.println("Invalid token");
        }
        filterChain.doFilter(request,response);
    }
}
