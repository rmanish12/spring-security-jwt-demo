package com.demo.springsecuritydemo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Authorized");
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    public ResponseEntity<String> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
    }
}
