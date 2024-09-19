package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.petadoption.center.util.Messages.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SocialMedia {

    @Pattern(regexp = "[a-zA-Z_0-9.-/]+", message = FACEBOOK_VALID)
    @Size(max = 100, message = CHARACTERS_LIMIT)
    private String facebook;

    @Pattern(regexp = "[a-zA-Z_0-9.-@]+", message = INSTAGRAM_VALID)
    @Size(max = 30, message = CHARACTERS_LIMIT)
    private String instagram;

    @Pattern(regexp = "[a-zA-Z_0-9.-/]+", message = TWITTER_VALID)
    @Size(max = 30, message = CHARACTERS_LIMIT)
    private String twitter;

    @Pattern(regexp = "[a-zA-Z_0-9.-/]+", message = YOUTUBE_VALID)
    @Size(max = 100, message = CHARACTERS_LIMIT)
    private String youtube;
}