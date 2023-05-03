package com.spotify.test.client;

import com.spotify.test.exception.ArtistNotFoundException;
import com.spotify.test.model.Artist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
public class SpotifyApiClient {

  private final WebClient spotifyWebClient;

  public Artist getArtistDetails(String artistId) {

    return spotifyWebClient
        .get()
        .uri("/artists/" + artistId)
        .retrieve()
        .onStatus(
            HttpStatus.NOT_FOUND::equals,
            clientResponse ->
                clientResponse
                    .bodyToMono(Artist.class)
                    .map(
                        artist ->
                            new ArtistNotFoundException(
                                "Provided artist not found in Spotify API, artistId: " + artistId)))
        .bodyToMono(Artist.class)
        .block();
  }
}
