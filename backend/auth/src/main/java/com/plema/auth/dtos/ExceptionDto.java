package com.plema.auth.dtos;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ExceptionDto {
    private HttpStatusCode statusCode;
    private String message;

}
