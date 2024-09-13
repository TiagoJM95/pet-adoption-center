package com.petadoption.center.util.factory;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.model.embeddable.SocialMedia;

public class SocialMediaFactory {
    public static SocialMedia createSocialMedia(OrgCreateDto dto) {
        return SocialMedia.builder()
                .facebook(dto.facebook())
                .instagram(dto.instagram())
                .twitter(dto.twitter())
                .youtube(dto.youtube())
                .build();
    }
}