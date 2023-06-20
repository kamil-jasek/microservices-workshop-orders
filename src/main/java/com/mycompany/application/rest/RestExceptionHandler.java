package com.mycompany.application.rest;

import com.mycompany.application.exception.DomainIllegalArgumentException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
final class RestExceptionHandler {

    record ErrorMessage(String message) {
    }

    @ExceptionHandler(DomainIllegalArgumentException.class)
    ResponseEntity<ErrorMessage> handle(DomainIllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorMessage> handle(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException ex) {
        final var errorMessages = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(toList());
        return ResponseEntity.badRequest().body(createErrorMessage(errorMessages));
    }

    private Map<String, List<String>> createErrorMessage(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
