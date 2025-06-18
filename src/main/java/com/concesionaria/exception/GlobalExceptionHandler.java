// File: src/main/java/com/concesionaria/exception/GlobalExceptionHandler.java
package com.concesionaria.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDup(DuplicateResourceException ex) {
        return ResponseEntity
            .badRequest()
            .body(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", ex.getMessage()
            ));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
            .status(404)
            .body(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", ex.getMessage()
            ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        return ResponseEntity
            .status(500)
            .body(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", "Error interno: " + ex.getMessage()
            ));
    }
}
