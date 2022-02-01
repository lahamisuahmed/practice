package com.practice.service.Impl;

import com.practice.dto.request.LoginRequest;
import com.practice.dto.response.LoginResponse;
import com.practice.exception.ApiException;
import com.practice.model.Role;
import com.practice.model.User;
import com.practice.service.AuthenticationService;
import com.practice.util.JwtTokenUtil;
import com.practice.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import static com.practice.constants.AppConstants.ACCESS_TOKEN;
import static com.practice.constants.AppConstants.REFRESH_TOKEN;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    //<editor-fold desc="LOGIN">
    public LoginResponse login(LoginRequest dto) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User userDetails = (User) authentication.getPrincipal();

            Map<String, String> tokens = jwtTokenUtil.generateToken(userDetails);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(tokens.get(ACCESS_TOKEN));
            loginResponse.setRefreshToken(tokens.get(REFRESH_TOKEN));
            loginResponse.setUserId(String.valueOf(userDetails.getId()));
            loginResponse.setEmail(userDetails.getEmail());
            loginResponse.setPhone(userDetails.getPhone());
            loginResponse.setRoles(userDetails.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

            return loginResponse;
        }catch (DisabledException ex) {
            ex.printStackTrace();
            LOGGER.error(Marker.ANY_NON_NULL_MARKER, ex);
            throw new ApiException(HttpStatus.UNAUTHORIZED, ResponseUtil.buildErrorResponse("401", "UNAUTHORIZED", "Please verify your account before you proceed to login"));
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            LOGGER.error(Marker.ANY_NON_NULL_MARKER, ex);
            throw new ApiException(HttpStatus.UNAUTHORIZED, ResponseUtil.buildErrorResponse("401", "UNAUTHORIZED", "Username or password is incorrect"));
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error(Marker.ANY_NON_NULL_MARKER, ex);
            throw new ApiException(HttpStatus.UNAUTHORIZED, ResponseUtil.buildErrorResponse("500", "INTERNAL_SERVER_ERROR", "Oooops!!! an error occurred from our end, please try again"));
        }
    }
    //</editor-fold>
}
