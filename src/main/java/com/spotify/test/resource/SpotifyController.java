package com.spotify.test.resource;

import com.spotify.test.exception.ArtistNotFoundException;
import com.spotify.test.model.Artist;
import com.spotify.test.model.ErrorResponse;
import com.spotify.test.service.SpotifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(
      summary = "Fetches the Artist details from Spotify API",
      description = "Fetches the Artist details by artist id from Spotify API")
  @ApiResponse(
      responseCode = "200",
      description = "Successfully fetched the Artist details",
      content = @Content(schema = @Schema(implementation = Artist.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Artist id not found in Spotify API",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Bad Artist id passed in input",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Internal server occurred",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  @GetMapping(value = "/artist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Artist> getArtistDetails(@PathVariable String id)
      throws ArtistNotFoundException {

    return ResponseEntity.ok(spotifyService.getArtist(id));
  }
}
