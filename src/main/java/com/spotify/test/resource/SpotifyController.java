package com.spotify.test.resource;

import com.spotify.test.exception.ArtistNotFoundException;
import com.spotify.test.model.Artist;
import com.spotify.test.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spotify/api/v1")
public class SpotifyController {

  private final SpotifyService spotifyService;

  @GetMapping(value = "/artist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Artist> getArtistDetails(@PathVariable String id)
      throws ArtistNotFoundException {

    return ResponseEntity.ok(spotifyService.getArtist(id));
  }
}
