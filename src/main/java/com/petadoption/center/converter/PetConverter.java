package com.petadoption.center.converter;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.model.Pet;
import com.petadoption.center.util.aggregator.PetCreateContext;
import com.petadoption.center.util.aggregator.PetGetContext;

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
                .gender(pet.getGender().getDescription())
                .coat(pet.getCoat().getDescription())
                .size(pet.getSize().getDescription())
                .age(pet.getAge().getDescription())
                .description(pet.getDescription())
                .imageUrl(pet.getImageUrl())
                .isAdopted(pet.getIsAdopted())
                .attributes(pet.getAttributes())
                .dateAdded(pet.getDateAdded())
                .organization(context.organization())
                .build();
    }
}
