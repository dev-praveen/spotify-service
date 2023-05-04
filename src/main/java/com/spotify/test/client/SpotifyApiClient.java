package com.spotify.test.client;

import com.spotify.test.exception.ArtistNotFoundException;
import com.spotify.test.exception.SpotifyApiException;
import com.spotify.test.model.Artist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                Mono.error(
                    new ArtistNotFoundException(
                        "Provided artist not found in Spotify API, artistId: " + artistId,
                        clientResponse.statusCode().value())))
        .onStatus(
            HttpStatus.BAD_REQUEST::equals,
            clientResponse ->
                Mono.error(
                    new ArtistNotFoundException(
                        "Bad request from Spotify API, artistId: " + artistId,
                        clientResponse.statusCode().value())))
        .onStatus(
            HttpStatus.INTERNAL_SERVER_ERROR::equals,
            clientResponse ->
                Mono.error(
                    new SpotifyApiException(
                        "Exception occurred while making a Spotify API call ",
                        clientResponse.statusCode().value())))
        .bodyToMono(Artist.class)
        .block();
  }
}
