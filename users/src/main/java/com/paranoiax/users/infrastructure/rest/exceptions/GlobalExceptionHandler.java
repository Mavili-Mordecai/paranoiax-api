package com.paranoiax.users.infrastructure.rest.exceptions;

import com.paranoiax.users.domain.exceptions.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<ApiErrorResponse>> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(
                MDC.get("traceId"),
                request.getRequestURI(),
                new ApiErrorResponse(errors)
        ));
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleDomainException(DomainException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(
                MDC.get("traceId"),
                request.getRequestURI(),
                new DomainErrorResponse(e.getCode().name(), e.getMessage(), e.getArgs())
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<ApiErrorCode>> handleBadRequest(HttpMessageNotReadableException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(
                MDC.get("traceId"),
                request.getRequestURI(),
                ApiErrorCode.MALFORMED_JSON
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<ApiErrorCode>> handleAll(Exception e, HttpServletRequest request) {
        log.error("Internal server error: ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(
                MDC.get("traceId"),
                request.getRequestURI(),
                ApiErrorCode.INTERNAL_SERVER_ERROR
        ));
    }
}
