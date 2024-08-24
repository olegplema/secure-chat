package com.plema.message.mappers;

import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import io.grpc.Status;

@Mapper(componentModel = "spring")
public interface GrpcStatusMapper {
    default HttpStatus map(Status.Code statusCode) {
        switch (statusCode) {
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case INTERNAL:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            case ALREADY_EXISTS:
                return HttpStatus.CONFLICT;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
