package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.*;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.util.aggregator.PetCreateContext;
import com.petadoption.center.util.aggregator.PetGetContext;

import static com.petadoption.center.enums.Ages.getAgeByDescription;
import static com.petadoption.center.enums.Coats.getCoatByDescription;
import static com.petadoption.center.enums.Genders.getGenderByDescription;
import static com.petadoption.center.enums.Sizes.getSizeByDescription;

public class PetConverter {

    public static Pet toModel(PetCreateDto pet, PetCreateContext context) {
        return Pet.builder()
                .name(pet.name())
                .species(context.species())
                .primaryBreed(context.primaryBreed())
                .secondaryBreed(context.secondaryBreed())
                .primaryColor(context.primaryColor())
                .secondaryColor(context.secondaryColor())
                .tertiaryColor(context.tertiaryColor())
                .gender(context.gender())
                .coat(context.coat())
                .size(context.size())
                .age(context.age())
                .description(pet.description())
                .imageUrl(pet.imageUrl())
                .isAdopted(pet.isAdopted())
                .attributes(context.attributes())
                .organization(context.organization())
                .build();
    }

    public static PetGetDto toDto(Pet pet, PetGetContext context) {
        return PetGetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(context.species())
                .primaryBreed(context.primaryBreed())
                .secondaryBreed(context.secondaryBreed())
                .primaryColor(context.primaryColor())
                .secondaryColor(context.secondaryColor())
                .tertiaryColor(context.tertiaryColor())
                .gender(pet.getGender())
                .coat(pet.getCoat())
                .size(pet.getSize())
                .age(pet.getAge())
                .description(pet.getDescription())
                .imageUrl(pet.getImageUrl())
                .isAdopted(pet.getIsAdopted())
                .attributes(pet.getAttributes())
                .dateAdded(pet.getDateAdded())
                .organization(context.organization())
                .build();
    }
}
