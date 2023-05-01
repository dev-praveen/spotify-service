package com.spotify.test.resource;

import com.spotify.test.client.SpotifyApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spotify/api/v1")
@RequiredArgsConstructor
public class SpotifyController {

  private final SpotifyApiClient spotifyApiClient;

  @GetMapping("/artist/{id}")
  public String getArtistDetails(@PathVariable String id) {

    return spotifyApiClient.getArtistDetails(id);
  }
}
