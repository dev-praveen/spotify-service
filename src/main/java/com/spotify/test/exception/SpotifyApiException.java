package com.spotify.test.exception;

import lombok.Getter;

@Getter
public class SpotifyApiException extends RuntimeException {

  private final int statusCode;

  public SpotifyApiException(String msg, int statusCode) {

    super(msg);
    this.statusCode = statusCode;
  }
}
