package com.spotify.test.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.test.model.Artist;
import com.spotify.test.service.SpotifyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    value = SpotifyController.class)
    //properties = {"spring.cloud.config.enabled=false"})
@AutoConfigureMockMvc(addFilters = false)
class SpotifyControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private SpotifyService spotifyService;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void shouldFetchArtistDetails() throws Exception {

    when(spotifyService.getArtist(anyString())).thenReturn(getArtistDetails());

    final var resultActions =
        mockMvc
            .perform(get("/spotify/api/v1/artist/{id}", "0TnOYISbd1XYRBk9myaseg"))
            .andExpect(status().isOk());

    final var response = resultActions.andReturn().getResponse();
    assertThat(response).isNotNull();
  }

  private Artist getArtistDetails() throws JsonProcessingException {

    final var artistJson =
        """
                {
                    "followers": {
                        "href": null,
                        "total": 9961944
                    },
                    "genres": [
                        "dance pop",
                        "miami hip hop",
                        "pop"
                    ],
                    "href": "https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg",
                    "id": "0TnOYISbd1XYRBk9myaseg",
                    "images": [
                        {
                            "height": 640,
                            "url": "https://i.scdn.co/image/ab6761610000e5ebfc9d2abc85b6f4bef77f80ea",
                            "width": 640
                        },
                        {
                            "height": 320,
                            "url": "https://i.scdn.co/image/ab67616100005174fc9d2abc85b6f4bef77f80ea",
                            "width": 320
                        },
                        {
                            "height": 160,
                            "url": "https://i.scdn.co/image/ab6761610000f178fc9d2abc85b6f4bef77f80ea",
                            "width": 160
                        }
                    ],
                    "name": "Pitbull",
                    "popularity": 83,
                    "type": "artist",
                    "uri": "spotify:artist:0TnOYISbd1XYRBk9myaseg",
                    "external_urls": {
                        "spotify": "https://open.spotify.com/artist/0TnOYISbd1XYRBk9myaseg"
                    }
                }
        """;
    return objectMapper.readValue(artistJson, Artist.class);
  }
}
