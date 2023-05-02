package com.spotify.test.resource;

import com.spotify.test.model.Artist;
import com.spotify.test.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spotify/api/v1")
public class SpotifyController {

  private final SpotifyService spotifyService;

  @GetMapping("/artist/{id}")
  public Artist getArtistDetails(@PathVariable String id) {
    return spotifyService.getArtist(id);
  }
}
