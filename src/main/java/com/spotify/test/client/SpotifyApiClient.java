package com.spotify.test.client;

import com.spotify.test.model.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class SpotifyApiClient {

  private final WebClient spotifyWebClient;

  public Artist getArtistDetails(String artistId) {

    return spotifyWebClient
        .get()
        .uri("/artists/" + artistId)
        .retrieve()
        .bodyToMono(Artist.class)
        .block();
  }
}
