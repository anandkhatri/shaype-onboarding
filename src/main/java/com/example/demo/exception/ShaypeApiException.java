package com.example.demo.exception;

import lombok.Getter;
import org.springframework.web.client.RestClientResponseException;
import shaype.openapi.example.model.ErrorResponse;

@Getter
public class ShaypeApiException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public ShaypeApiException(RestClientResponseException e) {
        super(e.getMessage());
        this.errorResponse = e.getResponseBodyAs(ErrorResponse.class);
    }
}
