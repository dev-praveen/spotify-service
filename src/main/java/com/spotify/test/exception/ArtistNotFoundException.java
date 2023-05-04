package com.spotify.test.exception;

import lombok.Getter;

@Getter
public class ArtistNotFoundException extends RuntimeException {

  private final int statusCode;

  public ArtistNotFoundException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }
}
