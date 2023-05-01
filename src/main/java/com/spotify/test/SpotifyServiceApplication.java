package com.spotify.test;

import com.spotify.test.client.SpotifyApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpotifyServiceApplication implements CommandLineRunner {

  @Autowired SpotifyApiClient spotifyApiClient;

  public static void main(String[] args) {
    SpringApplication.run(SpotifyServiceApplication.class, args);
  }

  @Override
  public void run(String... args) {

    final var artistDetails = spotifyApiClient.getArtistDetails("0TnOYISbd1XYRBk9myaseg");
    log.info("Spotify Artist details: {}", artistDetails);
  }
}
