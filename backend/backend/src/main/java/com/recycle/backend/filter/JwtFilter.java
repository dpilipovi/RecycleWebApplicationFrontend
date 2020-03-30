package com.recycle.backend.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.recycle.backend.services.CustomUserDetailsService;
import com.recycle.backend.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUitl;

    @Autowired
    private CustomUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
                String token=null;
                String username=null;
                String authorizationHeader = request.getHeader("Authorization");

               // System.out.println(request.getHeader("Content-type"));

                //System.out.println(authorizationHeader);

                if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                {
                    token=authorizationHeader.substring(7);
                    username=jwtUitl.extractUsername(token);

                    //System.out.println(token);
                }
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
             {
                UserDetails userDetails=service.loadUserByUsername(username);

                if(jwtUitl.validateToken(token, userDetails))
                {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
             }
             filterChain.doFilter(request, response);
    }

}