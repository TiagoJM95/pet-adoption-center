package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SocialMedia {


    @Pattern(regexp = WEBSITE_REGEX, message = FACEBOOK_VALID)
    @Size(max = 255, message = CHARACTERS_LIMIT)
    private String facebook;

    @Pattern(regexp = INSTAGRAM_REGEX, message = INSTAGRAM_VALID)
    @Size(max = 255, message = CHARACTERS_LIMIT)
    private String instagram;

    @Pattern(regexp = TWITTER_REGEX, message = TWITTER_VALID)
    @Size(max = 255, message = CHARACTERS_LIMIT)
    private String twitter;

    @Pattern(regexp = WEBSITE_REGEX, message = YOUTUBE_VALID)
    @Size(max = 100, message = CHARACTERS_LIMIT)
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