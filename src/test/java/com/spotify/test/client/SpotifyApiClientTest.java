package com.spotify.test.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Disabled
public class SpotifyApiClientTest {

  static WireMockServer wireMockServer;

  @BeforeAll
  static void beforeAll() {
    wireMockServer = new WireMockServer(wireMockConfig().port(8089));
  }

  @BeforeEach
  void setUp() {
    wireMockServer.start();
  }

  @AfterEach
  void tearDown() {
    wireMockServer.stop();
  }

  @AfterAll
  static void afterAll() {
    wireMockServer.resetAll();
  }
}
