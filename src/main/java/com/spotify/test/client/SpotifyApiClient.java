package com.spotify.test.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class SpotifyApiClient {

  private final WebClient spotifyWebClient;

  public String getArtistDetails(String artistId) {

    return spotifyWebClient
        .get()
        .uri("/artists/" + artistId)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
