package com.project.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotInDatabaseException.class)
    public ResponseEntity<ErrorInfo> handleUserNotInDatabaseException(UserNotInDatabaseException ex) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ToDoException.class)
    public ResponseEntity<ErrorInfo> handleToDoException(ToDoException ex) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<ErrorInfo> handleUnsupportedEncodingException(UnsupportedEncodingException ex) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotLoggedException.class)
    public ResponseEntity<ErrorInfo> handleUserNotLoggedException(UserNotLoggedException ex) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorInfo> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT, LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setTimeStamp(LocalDateTime.now());
        errorInfo.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        errorInfo.setErrorMessage(errorMessage);
        log.error("MethodArgumentNotValidException occurred: {}", errorMessage, ex);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setTimeStamp(LocalDateTime.now());
        errorInfo.setHttpStatusCode(HttpStatus.BAD_REQUEST);
        String errorMessage = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
        errorInfo.setErrorMessage(errorMessage);
        log.error("ConstraintViolationException occurred: {}", errorMessage, ex);
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
