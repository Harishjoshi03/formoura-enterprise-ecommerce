package com.formoura.exception.handler;

import com.formoura.exception.exception.BusinessException;
import com.formoura.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessException(BusinessException ex){

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Business Error")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

    }

}