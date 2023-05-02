package com.spotify.test.service;

import com.spotify.test.client.SpotifyApiClient;
import com.spotify.test.model.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotifyService {

  private final SpotifyApiClient spotifyApiClient;

  public Artist getArtist(String id) {
    return spotifyApiClient.getArtistDetails(id);
  }
}
