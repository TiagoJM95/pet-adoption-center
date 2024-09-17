package com.petadoption.center.dto.user;

import com.petadoption.center.model.Pet;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserFavoritePetsDto(
        Set<Pet> favoritePets
) {}