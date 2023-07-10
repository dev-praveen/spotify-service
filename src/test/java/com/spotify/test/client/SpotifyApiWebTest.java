package com.spotify.test.client;

import com.spotify.test.model.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpotifyApiWebTest {

  @InjectMocks private SpotifyApiClient spotifyApiClient;

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private WebClient webClient;

  private WebTestClient webTestClient;

  @BeforeEach
  public void setUp() {
    // webClient = WebClient.create("https://api.spotify.com/v1");
    webTestClient = WebTestClient.bindToController(spotifyApiClient).build();
  }

  @Test
  public void testGetArtistDetails_Success() {
    // Mock the response from the Spotify API
    Artist artist = Artist.builder().build();
    when(webClient
            .get()
            .uri("/artists/{artistId}", "0TnOYISbd1XYRBk9myaseg")
            .retrieve()
            .bodyToMono(Artist.class))
        .thenReturn(Mono.just(artist));

    // Send the request to the API and assert the response
    webTestClient
        .get()
        .uri("/artists/{artistId}", "0TnOYISbd1XYRBk9myaseg")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Artist.class)
        .isEqualTo(artist);
  }
}
