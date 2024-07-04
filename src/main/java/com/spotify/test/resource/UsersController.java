package com.spotify.test.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.test.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users-api")
public class UsersController {

  private final RestClient restClient;

  @GetMapping("/fetch")
  public ResponseEntity<List<User>> getUsers() {

    final var responseEntity =
        restClient
            .get()
            .uri("/users")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<User>>() {});

    final var userList = responseEntity.getBody();
    return ResponseEntity.ok(userList);
  }
}
