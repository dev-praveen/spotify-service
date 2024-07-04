package com.spotify.test.resource;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.client.RestClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class UsersControllerTest {

  @RegisterExtension
  static WireMockExtension wireMockExtension =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private UsersController usersController;

  @BeforeEach
  public void init() {
    // Initialize WebClient with WireMock server base URL
    RestClient restClient = RestClient.builder().baseUrl(wireMockExtension.baseUrl()).build();
    usersController = new UsersController(restClient);
  }

  @Test
  void shouldGetUsers() {

    wireMockExtension.stubFor(
        get(urlEqualTo("/users"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("users.json")));
    final var users = usersController.getUsers();
    assertThat(users).isNotNull();
    assertThat(users.getBody()).hasSize(2);
  }
}
