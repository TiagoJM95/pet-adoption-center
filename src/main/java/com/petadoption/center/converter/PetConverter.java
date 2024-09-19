package com.petadoption.center.converter;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.model.Pet;

public class PetConverter {

    public static Pet toModel(PetCreateDto dto) {
        if (dto == null ) return null;
        return Pet.builder()
                .name(dto.name())
                .description(dto.description())
                .imageUrl(dto.imageUrl())
                .isAdopted(dto.isAdopted())
                .attributes(dto.attributes())
                .build();
    }
    
    public static Pet toModel(PetGetDto dto) {
        if (dto == null) return null;
        return Pet.builder()
                .id(dto.id())
                .name(dto.name())
                .species(SpeciesConverter.toModel(dto.speciesDto()))
                .primaryBreed(BreedConverter.toModel(dto.primaryBreedDto()))
                .secondaryBreed(BreedConverter.toModel(dto.secondaryBreedDto()))
                .primaryColor(ColorConverter.toModel(dto.primaryColorDto()))
                .secondaryColor(ColorConverter.toModel(dto.secondaryColorDto()))
                .tertiaryColor(ColorConverter.toModel(dto.tertiaryColorDto()))
                .description(dto.description())
                .imageUrl(dto.imageUrl())
                .isAdopted(dto.isAdopted())
                .attributes(dto.attributes())
                .organization(OrgConverter.toModel(dto.organizationDto()))
                .build();
    }
    
    public static PetGetDto toDto(Pet pet) {
        if (pet == null) return null;
        return PetGetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .speciesDto(SpeciesConverter.toDto(pet.getSpecies()))
                .primaryBreedDto(BreedConverter.toDto(pet.getPrimaryBreed()))
                .secondaryBreedDto(BreedConverter.toDto(pet.getSecondaryBreed()))
                .primaryColorDto(ColorConverter.toDto(pet.getPrimaryColor()))
                .secondaryColorDto(ColorConverter.toDto(pet.getSecondaryColor()))
                .tertiaryColorDto(ColorConverter.toDto(pet.getTertiaryColor()))
                .description(pet.getDescription())
                .imageUrl(pet.getImageUrl())
                .isAdopted(pet.isAdopted())
                .attributes(pet.getAttributes())
                .dateAdded(pet.getDateAdded())
                .organizationDto(OrgConverter.toDto(pet.getOrganization()))
                .build();
    }
}