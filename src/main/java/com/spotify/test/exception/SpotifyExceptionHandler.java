package com.spotify.test.exception;

import java.time.LocalDateTime;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.spotify.test.model.ErrorResponse;

@RestControllerAdvice
public class SpotifyExceptionHandler {

  @ExceptionHandler(SpotifyApiException.class)
  public ResponseEntity<ErrorResponse> handleException(SpotifyApiException ex, WebRequest request) {

    final var statusCode = ex.getStatusCode();
    final var path = ((ServletWebRequest) request).getRequest().getRequestURI();
    final var errorResponse =
        ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(statusCode)
            .error(ex.getMessage())
            .path(path)
            .build();

    return ResponseEntity.status(statusCode).body(errorResponse);
  }

  @ExceptionHandler(ArtistNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleException(
      ArtistNotFoundException ex, WebRequest request) {

    final var statusCode = ex.getStatusCode();
    final var path = ((ServletWebRequest) request).getRequest().getRequestURI();
    final var errorResponse =
        ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(statusCode)
            .error(ex.getMessage())
            .path(path)
            .build();

    return ResponseEntity.status(statusCode).body(errorResponse);
  }
}
