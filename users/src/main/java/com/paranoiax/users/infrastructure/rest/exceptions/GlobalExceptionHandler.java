package com.paranoiax.users.infrastructure.rest.exceptions;

import com.paranoiax.users.domain.exceptions.*;
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
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Domain exceptions
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleDomainException(DomainException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(UnauthorizeException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleUnauthorizeException(UnauthorizeException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.UNAUTHORIZED, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.FORBIDDEN, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(RevokedException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleRevokedException(RevokedException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.FORBIDDEN, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.NOT_FOUND, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(AlreadyTakenException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleAlreadyTakenException(AlreadyTakenException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.CONFLICT, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(AlreadyRevokedException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleAlreadyRevokedException(AlreadyRevokedException e, HttpServletRequest request) {
        return getErrorResponse(HttpStatus.CONFLICT, request, e.getCode(), e.getMessage(), e.getArgs());
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleRateLimitExceededException(RateLimitExceededException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Retry-After", String.valueOf(e.getRetryAfterMillis()))
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                DomainErrorCode.RATE_LIMIT_EXCEEDED.name(),
                                DomainErrorCode.RATE_LIMIT_EXCEEDED.getDefaultMessage(),
                                null
                        )
                ));
    }

    // Validation and parsing
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<ApiErrorResponse>> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacement) -> existing));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new ApiErrorResponse(errors)
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<ApiErrorCode>> handleMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        ApiErrorCode.MESSAGE_NOT_READABLE
                ));
    }

    // Api Exceptions
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleMissingHeader(MissingRequestHeaderException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                ApiErrorCode.MISSING_REQUEST_HEADER.name(),
                                "Missing request header: " + ex.getHeaderName(),
                                Map.of("resource", ex.getHeaderName())
                        )
                ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String[] supportedMethods = e.getSupportedMethods();
        List<String> methodsList = supportedMethods != null ? Arrays.asList(supportedMethods) : Collections.emptyList();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                ApiErrorCode.METHOD_NOT_ALLOWED.name(),
                                "Method not allowed: " + e.getMethod(),
                                Map.of("supportedMethods", methodsList)
                        )
                ));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                ApiErrorCode.UNSUPPORTED_MEDIA_TYPE.name(),
                                "Unsupported media type: " + ex.getContentType(),
                                null
                        )
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                ApiErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH.name(),
                                "Method argument type mismatch: " + ex.getName(),
                                Map.of("argument", ex.getName())
                        )
                ));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse<DomainErrorResponse>> handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(
                                ApiErrorCode.NOT_FOUND.name(),
                                "Resource path not found: " + e.getResourcePath(),
                                Map.of("resource", e.getResourcePath(), "method", e.getHttpMethod().name())
                        )
                ));
    }

    // Other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<ApiErrorCode>> handleAllExceptions(Exception e, HttpServletRequest request) {
        log.error("Internal server error at URI: {}", request.getRequestURI(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        ApiErrorCode.INTERNAL_SERVER_ERROR
                ));
    }

    private static @NonNull ResponseEntity<ErrorResponse<DomainErrorResponse>> getErrorResponse(
            HttpStatus status,
            HttpServletRequest request,
            DomainErrorCode code,
            String message,
            Map<String, Object> args
    ) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.of(
                        MDC.get("traceId"),
                        request.getRequestURI(),
                        new DomainErrorResponse(code.name(), message, args)
                ));
    }
}