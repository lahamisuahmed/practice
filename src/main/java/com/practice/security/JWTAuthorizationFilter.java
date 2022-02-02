package com.practice.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.util.JwtTokenUtil;
import com.practice.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    public JWTAuthorizationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Optional<String> token = parseJwt(request);

        if (token.isPresent()) {

            if (!jwtTokenUtil.isValidToken(token.get())) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getOutputStream().write(objectMapper.writeValueAsBytes(ResponseUtil
                        .buildErrorResponse("401", "UNAUTHORIZED", "you are not authorized to access this resource")));
                return;
            }

            try {

                if(jwtTokenUtil.isTokenExpired(token.get())) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getOutputStream().write(objectMapper.writeValueAsBytes(ResponseUtil
                            .buildErrorResponse("401", "UNAUTHORIZED", "Token expired")));
                    return;
                }

                UserDetails userDetails = jwtTokenUtil.getUserDetailsFromToken(token.get());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getOutputStream().write(objectMapper.writeValueAsBytes(ResponseUtil
                        .buildErrorResponse("401", "UNAUTHORIZED", ex.getMessage())));
                return;
            }

        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(ResponseUtil
                    .buildErrorResponse("400", "BAD_REQUEST", "no token provided")));
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        Set<String> doNotFilterUrls = new HashSet<>();
        doNotFilterUrls.add("/api/auth");
        doNotFilterUrls.add("/api/password-reset/**");
        doNotFilterUrls.add("/api/signup/**");

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        return doNotFilterUrls.stream().anyMatch(pattern -> antPathMatcher.match(pattern, request.getRequestURI()));
    }

    private Optional<String> parseJwt(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.split(" ")[1].trim());
        }

        return Optional.empty();
    }

}
