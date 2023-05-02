package com.spotify.test.exception;

import lombok.Getter;

@Getter
public class SpotifyApiException extends RuntimeException {

  private final int statusCode;

  public SpotifyApiException(Throwable tw, int statusCode) {

    super(tw);
    this.statusCode = statusCode;
  }
}
