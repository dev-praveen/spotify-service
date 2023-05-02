package com.spotify.test.client;

import com.spotify.test.exception.SpotifyApiException;
import com.spotify.test.model.Artist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RequiredArgsConstructor
public class SpotifyApiClient {

  private final WebClient spotifyWebClient;

  public Artist getArtistDetails(String artistId) {

    try {
      return spotifyWebClient
          .get()
          .uri("/artists/" + artistId)
          .retrieve()
          .bodyToMono(Artist.class)
          .block();
    } catch (WebClientResponseException e) {
      log.error(
          "Exception occured while fetching artist details from Spotify API, artistId: {}",
          artistId);
      throw new SpotifyApiException(e, e.getStatusCode().value());
    }
  }
}
