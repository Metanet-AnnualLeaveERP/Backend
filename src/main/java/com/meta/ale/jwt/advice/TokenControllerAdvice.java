package com.meta.ale.jwt.advice;

import com.meta.ale.jwt.TokenRefreshException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
         log.error("RefreshTokenError"
                + HttpStatus.FORBIDDEN.value()
                + new Date()
                + ex.getMessage()
                + request.getDescription(false));
    }
}
