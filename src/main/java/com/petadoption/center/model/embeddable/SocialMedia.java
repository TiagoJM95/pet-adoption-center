package com.petadoption.center.model.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

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
    @Schema(description = "Facebook url", example = "https://www.facebook.com")
    private String facebook;

    @Pattern(regexp = "[a-zA-Z_0-9.-@]+", message = INSTAGRAM_VALID)
    @Size(max = 30, message = CHARACTERS_LIMIT)
    @Schema(description = "Instagram username", example = "animalRescue")
    private String instagram;

    @Pattern(regexp = "[a-zA-Z_0-9.-/]+", message = TWITTER_VALID)
    @Size(max = 30, message = CHARACTERS_LIMIT)
    @Schema(description = "Twitter username", example = "animalRescue")
    private String twitter;

    @Pattern(regexp = "[a-zA-Z_0-9.-/]+", message = YOUTUBE_VALID)
    @Size(max = 100, message = CHARACTERS_LIMIT)
    @Schema(description = "Youtube url", example = "https://www.youtube.com")
    private String youtube;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialMedia that = (SocialMedia) o;
        return Objects.equals(facebook, that.facebook) && Objects.equals(instagram, that.instagram) && Objects.equals(twitter, that.twitter) && Objects.equals(youtube, that.youtube);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facebook, instagram, twitter, youtube);
    }
}