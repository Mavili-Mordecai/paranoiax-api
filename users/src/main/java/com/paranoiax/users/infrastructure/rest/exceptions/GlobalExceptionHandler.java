package com.paranoiax.users.infrastructure.rest.exceptions;

import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.exceptions.DomainException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.exceptions.UnauthorizeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.NOT_FOUND, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(value = UnauthorizeException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleUnauthorizeException(UnauthorizeException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.UNAUTHORIZED, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleDomainException(DomainException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<ApiErrorResponse>> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new ApiErrorResponse(errors)
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<ApiErrorCode>> handleBadRequest(HttpMessageNotReadableException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        ApiErrorCode.MESSAGE_NOT_READABLE
                ));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                "UNSUPPORTED_MEDIA_TYPE",
                                "Expected multipart/form-data",
                                null
                        )
                ));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleMissingRequestHeaderException(
            MissingRequestHeaderException ex, HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                "MISSING_REQUEST_HEADER",
                                "Missing request header: " + ex.getHeaderName(),
                                Map.of("resource", ex.getHeaderName())
                        )
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<ApiErrorCode>> handleAll(Exception e, HttpServletRequest request) {
        log.error("Internal server error: ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        ApiErrorCode.INTERNAL_SERVER_ERROR
                ));
    }

    private static @NonNull ResponseEntity<ErrorResponse<DomainErrorResponse>> getErrorResponse(HttpStatus status, HttpServletRequest request, DomainErrorCode e, String e1, Map<String, Object> e2) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(e.name(), e1, e2)
                ));
    }
}
