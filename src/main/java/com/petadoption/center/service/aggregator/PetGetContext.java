package com.petadoption.center.service.aggregator;

import com.petadoption.center.dto.organization.OrgGetDto;
import lombok.Builder;

@Builder
public record PetGetContext(
        OrgGetDto organization,
        String species,
        String primaryBreed,
        String secondaryBreed,
        String primaryColor,
        String secondaryColor,
        String tertiaryColor
) {}