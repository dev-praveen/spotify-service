package com.spotify.test.client;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.spotify.test.exception.ArtistNotFoundException;
import com.spotify.test.exception.SpotifyApiException;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

class SpotifyApiClientTest {

  static WireMockServer wireMockServer;
  private SpotifyApiClient spotifyApiClient;

  @BeforeAll
  static void beforeAll() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
    configureFor(8089);
  }

  @AfterAll
  static void afterAll() {
    wireMockServer.stop();
  }

  @BeforeEach
  void setUp() {
    spotifyApiClient =
        new SpotifyApiClient(WebClient.builder().baseUrl("http://localhost:8089/v1").build());
    wireMockServer.resetAll();
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

    // Then
    assertThat(artistDetails).isNotNull();
    assertThat(artistDetails.getId()).isEqualTo(artistId);

    // verify wiremock was called
    verify(getRequestedFor(urlEqualTo("/v1/artists/" + artistId)));
  }

  @Test
  void shouldThrowArtistNotFoundExceptionForInvalidArtistId() {

    // Given
    String invalidArtistId = "invalid_artist_id";

    // Set up wiremock stub
    stubFor(
        get(urlPathMatching("/v1/artists/" + invalidArtistId))
            .willReturn(
                aResponse()
                    .withStatus(404)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                            {
                              "timestamp": "2023-05-05T22:03:23.6586682",
                              "status": 404,
                              "error": "Provided artist not found in Spotify API, artistId: invalid_artist_id",
                              "path": "/spotify/api/v1/artist/invalid_artist_id"
                            }
                        """)));

    // When
    // Then
    assertThatThrownBy(() -> spotifyApiClient.getArtistDetails(invalidArtistId))
        .isInstanceOf(ArtistNotFoundException.class)
        .hasMessageContaining(
            "Provided artist not found in Spotify API, artistId: " + invalidArtistId);
  }

  @Test
  void shouldThrowArtistNotFoundExceptionForBadId() {

    // Given
    String badArtistId = "bad_artist_id";

    // Set up wiremock stub
    stubFor(
        get(urlPathMatching("/v1/artists/" + badArtistId))
            .willReturn(
                aResponse()
                    .withStatus(400)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                            {
                              "timestamp": "2023-05-05T22:03:23.6586682",
                              "status": 400,
                              "error": "Bad request from Spotify API, artistId: bad_artist_id",
                              "path": "/spotify/api/v1/artist/bad_artist_id"
                            }
                        """)));

    // When
    // Then
    assertThatThrownBy(() -> spotifyApiClient.getArtistDetails(badArtistId))
        .isInstanceOf(ArtistNotFoundException.class)
        .hasMessageContaining("Bad request from Spotify API, artistId: " + badArtistId);
  }

  @Test
  void shouldThrowSpotifyApiException() {

    // Given
    String artistId = "0TnOYISbd1XYRBk9myaseg";

    // Set up wiremock stub
    stubFor(
        get(urlPathMatching("/v1/artists/" + artistId))
            .willReturn(
                aResponse()
                    .withStatus(500)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                            {
                              "timestamp": "2023-05-05T22:03:23.6586682",
                              "status": 500,
                              "error": "Exception occurred while making a Spotify API call ",
                              "path": "/spotify/api/v1/artist/0TnOYISbd1XYRBk9myaseg"
                            }
                        """)));

    // When
    // Then
    assertThatThrownBy(() -> spotifyApiClient.getArtistDetails(artistId))
        .isInstanceOf(SpotifyApiException.class)
        .hasMessageContaining("Exception occurred while making a Spotify API call ");
  }
}
