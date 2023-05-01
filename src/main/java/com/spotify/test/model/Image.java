package com.spotify.test.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.NONE)
public class Image {

  private int height;
  private String url;
  private int width;
}
