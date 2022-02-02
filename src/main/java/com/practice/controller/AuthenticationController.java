package com.practice.controller;

import com.practice.dto.MetaDTO;
import com.practice.dto.request.ApiRequest;
import com.practice.dto.request.LoginRequest;
import com.practice.dto.response.ApiResponse;
import com.practice.dto.response.LoginResponse;
import com.practice.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid ApiRequest<LoginRequest> loginRequest){

        MetaDTO metaDTO = new MetaDTO();
        metaDTO.setMessage("Request processed successfully");
        metaDTO.setStatus("200");
        metaDTO.setInfo("Success");

        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>();

        LoginResponse response = authenticationService.login(loginRequest.getData());

        apiResponse.setMetaDTO(metaDTO);
        apiResponse.setData(response);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
