package com.backend_torch.Data.Persistence.API.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import reactor.core.Exceptions;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Unified response builder that ensures the HTTP Status Header
     * matches the JSON body intent.
     */
    private ResponseEntity<Map<String, Object>> build(String statusField, String message, int httpStatusCode) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", statusField != null ? statusField : "error");
        body.put("message", message != null ? message : "Unexpected error");

        return ResponseEntity.status(httpStatusCode).body(body);
    }

    // =========================
    // Custom Exception (Handles 502, 404, 400 manually thrown)
    // =========================
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handle(ApiException ex) {
        // Fallback to 400 if httpStatus is not set
        int code = ex.getHttpStatus() != 0 ? ex.getHttpStatus() : 400;

        // Ensure "502" status string for upstream failures, "error" for everything else
        String statusField = "502".equals(ex.getStatus()) ? "502" : "error";

        return build(statusField, ex.getMessage(), code);
    }

    // =========================
    // Validation Errors (422 Unprocessable Entity)
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors().isEmpty()
                ? "Validation failed"
                : ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return build("error", msg, 422);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBind(BindException ex) {
        String msg = ex.getAllErrors().isEmpty()
                ? "Validation failed"
                : ex.getAllErrors().get(0).getDefaultMessage();

        return build("error", msg, 422);
    }

    // =========================
    // 400 - Bad Request
    // =========================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadJson(HttpMessageNotReadableException ex) {
        return build("error", "Invalid request body (malformed JSON or wrong field types)", 400);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParams(MissingServletRequestParameterException ex) {
        return build("error", ex.getParameterName() + " is required", 400);
    }

    // =========================
    // 405 - Method Not Allowed
    // =========================
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return build("error", "Method not allowed", 405);
    }

    // =========================
    // 404 - Not Found
    // =========================
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResource(NoResourceFoundException ex, HttpServletRequest req) {
        return build("error", "Endpoint not found: " + req.getRequestURI(), 404);
    }
//    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<?> handleTypeMismatch(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
//        String name = ex.getName();
//        String type = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "required type";
//        String message = String.format("Parameter '%s' should be of type %s", name, type);
//        return build("error", message, 400);
//    }

    // =========================
    // Reactor / Timeout / Global Fallback
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAny(Exception ex) {
        Throwable root = Exceptions.unwrap(ex);

        // Handle WebClient timeouts or upstream failures -> 502
        if (root instanceof java.util.concurrent.TimeoutException) {
            return build("502", "Upstream service timeout", 502);
        }

        // If an ApiException was wrapped inside a Reactor exception
        if (root instanceof ApiException apiEx) {
            String statusField = "502".equals(apiEx.getStatus()) ? "502" : "error";
            return build(statusField, apiEx.getMessage(), apiEx.getHttpStatus());
        }

        // Default fallback -> 500 Internal Server Error
        return build("error", "Internal server error", 500);
    }
}