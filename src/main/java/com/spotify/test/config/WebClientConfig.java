package com.spotify.test.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spotify.test.client.SpotifyApiClient;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient spotifyWebClient(ClientRegistrationRepository clientRegistrationRepository) {

    final var registrationRepository =
        new InMemoryReactiveClientRegistrationRepository(
            clientRegistrationRepository.findByRegistrationId("spotify"));

    final var clientService =
        new InMemoryReactiveOAuth2AuthorizedClientService(registrationRepository);
    final var clientManager =
        new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
            registrationRepository, clientService);
    final var oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientManager);
    oauth2.setDefaultClientRegistrationId("spotify");

    return WebClient.builder().baseUrl("https://api.spotify.com/v1").filter(oauth2).build();
  }

  @Bean
  public SpotifyApiClient spotifyApiClient(WebClient spotifyWebClient) {
    return new SpotifyApiClient(spotifyWebClient);
  }

  @Bean
  RestClient restClient() {
    return RestClient.builder().baseUrl("https://jsonplaceholder.typicode.com").build();
  }

  /*@Bean
  public Jackson2ObjectMapperBuilderCustomizer customBuilder() {
    return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL);
  }*/
}
