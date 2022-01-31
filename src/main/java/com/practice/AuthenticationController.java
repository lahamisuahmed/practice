package com.practice;

import com.practice.dto.MetaDTO;
import com.practice.dto.request.ApiRequest;
import com.practice.dto.request.LoginRequest;
import com.practice.dto.response.ApiResponse;
import com.practice.dto.response.LoginResponse;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid ApiRequest<LoginRequest> loginRequest){

        MetaDTO metaDTO = new MetaDTO();
        metaDTO.setMessage("Request processed successfully");
        metaDTO.setStatus("200");
        metaDTO.setInfo("Success");

        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>();

        apiResponse.setMetaDTO(metaDTO);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
