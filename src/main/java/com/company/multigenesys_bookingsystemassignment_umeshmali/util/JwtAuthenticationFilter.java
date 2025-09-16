package com.company.multigenesys_bookingsystemassignment_umeshmali.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
        import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(JwtUtil jwtUtil) { this.jwtUtil = jwtUtil; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, jakarta.servlet.ServletException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        final String token = authHeader.substring(7);
        try {
            Jws<Claims> claimsJws = jwtUtil.parseClaims(token);
            Claims claims = claimsJws.getBody();
            String username = claims.getSubject();
            Long uid = claims.get("uid", Number.class).longValue();
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get("roles");
            Set<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

            // store uid and username in principal as a simple map or custom principal object
            Map<String, Object> principal = new HashMap<>();
            principal.put("username", username);
            principal.put("uid", uid);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception ex) {
            // invalid token -> no auth
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
