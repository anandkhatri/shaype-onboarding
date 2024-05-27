package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;
import shaype.openapi.example.model.ErrorResponse;

@Getter
public class ShaypeApiException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public ShaypeApiException(RestClientResponseException e) {
        super(e.getMessage());
        this.errorResponse = e.getResponseBodyAs(ErrorResponse.class);
    }

    public ShaypeApiException(String errorMessage) {
        super(errorMessage);
        this.errorResponse =  new ErrorResponse()
                .message(errorMessage)
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
