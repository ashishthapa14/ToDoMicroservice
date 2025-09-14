package com.project.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorInfo {
    private String errorMessage;
    private HttpStatus httpStatusCode;
    private LocalDateTime timeStamp;
}
