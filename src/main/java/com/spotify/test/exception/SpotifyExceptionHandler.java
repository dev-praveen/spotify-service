package com.spotify.test.exception;

import java.time.LocalDateTime;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.spotify.test.model.ErrorResponse;

@RestControllerAdvice
public class SpotifyExceptionHandler {

    @ExceptionHandler(SpotifyApiException.class)
    public ResponseEntity<ErrorResponse> handleException(SpotifyApiException ex, WebRequest request) {
        HttpStatusCode httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        final var statusCode = ex.getStatusCode();
        final var path = ((ServletWebRequest) request).getRequest().getRequestURI();
        final var errorResponse = ErrorResponse.builder().timestamp(LocalDateTime.now()).status(statusCode)
                .error(ex.getMessage())
                .path(path).build();

        if (statusCode == 404) {
            httpStatusCode = HttpStatus.NOT_FOUND;
        } else if (statusCode == 400) {
            httpStatusCode = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(httpStatusCode).body(errorResponse);
    }
}
