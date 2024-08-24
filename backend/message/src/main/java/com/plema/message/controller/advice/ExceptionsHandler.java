package com.plema.message.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.plema.message.dtos.ExceptionDto;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionDto> handleResponseStatusException(ResponseStatusException ex) {
        ExceptionDto exceptionDto = ExceptionDto.builder().message(ex.getReason()).statusCode(ex.getStatusCode())
                .build();
        return ResponseEntity.status(ex.getStatusCode()).body(exceptionDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto exceptionDto = ExceptionDto.builder().message("Internal server error")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(500).body(exceptionDto);

    }

}
