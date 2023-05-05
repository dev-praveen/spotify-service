package com.spotify.test.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import static org.assertj.core.api.Assertions.assertThat;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

class SpotifyApiClientTest {

  private SpotifyApiClient spotifyApiClient;
  static WireMockServer wireMockServer;

  @BeforeAll
  static void beforeAll() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
    configureFor(8089);
  }

  @BeforeEach
  void setUp() {
    spotifyApiClient =
        new SpotifyApiClient(WebClient.builder().baseUrl("http://localhost:8089/v1").build());
    wireMockServer.resetAll();
  }

  @AfterAll
  static void afterAll() {
    wireMockServer.stop();
  }

  @Test
  void shouldFetchArtistDetailsByArtistId() {

    // Given
    String artistId = "0TnOYISbd1XYRBk9myaseg";

    // Set up wiremock stub
    stubFor(
        get(urlPathMatching("/v1/artists/" + artistId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("artist-details.json")));

    // When
    final var artistDetails = spotifyApiClient.getArtistDetails(artistId);

    // then
    assertThat(artistDetails).isNotNull();
    assertThat(artistDetails.getId()).isEqualTo(artistId);

    // verify wiremock was called
    verify(getRequestedFor(urlEqualTo("/v1/artists/" + artistId)));
  }
}
