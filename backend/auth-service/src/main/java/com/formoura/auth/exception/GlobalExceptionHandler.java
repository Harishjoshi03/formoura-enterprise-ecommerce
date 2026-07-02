package com.formoura.auth.exception;

import com.formoura.auth.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> alreadyExists(
            ResourceAlreadyExistsException ex){

        return ResponseEntity.status(HttpStatus.CONFLICT)

                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> notFound(
            ResourceNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)

                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> badCredentials(
            BadCredentialsException ex){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)

                .body(new ApiResponse<>(
                        false,
                        "Invalid Email or Password",
                        null));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> validation(
            MethodArgumentNotValidException ex){

        String error =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return ResponseEntity.badRequest()

                .body(new ApiResponse<>(
                        false,
                        error,
                        null));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> exception(
            Exception ex){

        return ResponseEntity.internalServerError()

                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null));

    }

}