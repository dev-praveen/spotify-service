package com.spotify.test.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.NONE)
public class Followers {

  private String href;
  private int total;
}
