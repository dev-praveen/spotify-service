package com.spotify.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.NONE)
public class Artist {

  @JsonProperty("external_urls")
  private ExternalUrls externalUrls;

  private Followers followers;
  private List<String> genres;
  private String href;
  private String id;
  private List<Image> images;
  private String name;
  private int popularity;
  private String type;
  private String uri;
}
