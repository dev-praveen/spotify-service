package com.spotify.test.client;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.spotify.test.exception.ArtistNotFoundException;
import com.spotify.test.exception.SpotifyApiException;
import com.spotify.test.model.Artist;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class SpotifyApiTest {

  @RegisterExtension
  static WireMockExtension wireMockExtension =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private SpotifyApiClient spotifyApiClient;

  @BeforeEach
  public void init() {
    // Initialize WebClient with WireMock server base URL
    WebClient webClient =
        WebClient.builder().baseUrl(wireMockExtension.baseUrl().concat("/v1")).build();
    spotifyApiClient = new SpotifyApiClient(webClient);
  }

  @Test
  void testGetArtistDetails_Success() {
    // Mock successful response from Spotify API
    String artistId = "0TnOYISbd1XYRBk9myaseg";

    wireMockExtension.stubFor(
        get(urlEqualTo("/v1/artists/" + artistId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("artist-details.json")));

    Artist artistDetails = spotifyApiClient.getArtistDetails(artistId);

    assertThat(artistDetails).isNotNull();
    assertThat(artistDetails.getId()).isEqualTo(artistId);
  }

  @Test
  void testGetArtistDetails_NotFound() {
    // Mock 404 Not Found response from Spotify API
    String artistId = "67890";

    wireMockExtension.stubFor(
        get(urlEqualTo("/v1/artists/" + artistId)).willReturn(aResponse().withStatus(404)));

    Assertions.assertThrows(
        ArtistNotFoundException.class, () -> spotifyApiClient.getArtistDetails(artistId));
  }

  @Test
  void testGetArtistDetails_BadRequest() {
    // Mock 400 Bad Request response from Spotify API
    String artistId = "67890";

    wireMockExtension.stubFor(
        get(urlEqualTo("/v1/artists/" + artistId)).willReturn(aResponse().withStatus(400)));

    Assertions.assertThrows(
        ArtistNotFoundException.class, () -> spotifyApiClient.getArtistDetails(artistId));
  }

  @Test
  void testGetArtistDetails_InternalServerError() {
    // Mock 500 Internal Server Error response from Spotify API
    String artistId = "67890";

    wireMockExtension.stubFor(
        get(urlEqualTo("/v1/artists/" + artistId)).willReturn(aResponse().withStatus(500)));

    Assertions.assertThrows(
        SpotifyApiException.class, () -> spotifyApiClient.getArtistDetails(artistId));
  }
}
