package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import shaype.openapi.example.model.ErrorResponse;

import java.net.ConnectException;

@Getter
public class ShaypeApiException extends RuntimeException {

    private final ErrorResponse errorResponse;


    public ShaypeApiException(RestClientException e) {
        super(e.getMessage());
        if (e.getCause() instanceof ConnectException) {
            this.errorResponse = new ErrorResponse()
                    .message(e.getCause().getMessage())
                    .status(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
        } else {
            this.errorResponse = ((HttpClientErrorException) e).getResponseBodyAs(ErrorResponse.class);
        }
    }

    public ShaypeApiException(String errorMessage) {
        super(errorMessage);
        this.errorResponse = new ErrorResponse()
                .message(errorMessage)
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
