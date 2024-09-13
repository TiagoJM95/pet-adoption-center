package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SocialMedia {
    private String facebook;
    private String instagram;
    private String twitter;
    private String youtube;
}
