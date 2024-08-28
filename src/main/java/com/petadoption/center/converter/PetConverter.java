package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.*;
import com.petadoption.center.model.embeddable.Attributes;

import static com.petadoption.center.enums.Ages.getAgeByDescription;
import static com.petadoption.center.enums.Coats.getCoatByDescription;
import static com.petadoption.center.enums.Genders.getGenderByDescription;
import static com.petadoption.center.enums.Sizes.getSizeByDescription;

public class PetConverter {

    public static Pet fromPetCreateDtoToModel(PetCreateDto pet,
                                              Species species,
                                              Breed primaryBreed,
                                              Breed secondaryBreed,
                                              Color primaryColor,
                                              Color secondaryColor,
                                              Color tertiaryColor,
                                              Organization organization) {

        Attributes attributes = new Attributes(
                pet.petSterilized(),
                pet.petVaccinated(),
                pet.petChipped(),
                pet.specialNeeds(),
                pet.houseTrained(),
                pet.goodWithKids(),
                pet.goodWithDogs(),
                pet.goodWithCats()
        );

        return Pet.builder()
                .name(pet.name())
                .species(species)
                .primaryBreed(primaryBreed)
                .secondaryBreed(secondaryBreed)
                .primaryColor(primaryColor)
                .secondaryColor(secondaryColor)
                .tertiaryColor(tertiaryColor)
                .gender(getGenderByDescription(pet.gender()).orElseThrow(RuntimeException::new))
                .coat(getCoatByDescription(pet.coat()).orElseThrow(RuntimeException::new))
                .size(getSizeByDescription(pet.size()).orElseThrow(RuntimeException::new))
                .age(getAgeByDescription(pet.age()).orElseThrow(RuntimeException::new))
                .description(pet.description())
                .imageUrl(pet.imageUrl())
                .isAdopted(pet.isAdopted())
                .attributes(attributes)
                .organization(organization)
                .build();
    }

    public static PetGetDto fromModelToPetGetDto(Pet pet,
                                                 OrgGetDto organization,
                                                 SpeciesGetDto species,
                                                 BreedGetDto primaryBreed,
                                                 BreedGetDto secondaryBreed,
                                                 ColorGetDto primaryColor,
                                                 ColorGetDto secondaryColor,
                                                 ColorGetDto tertiaryColor) {
        return new PetGetDto(
                pet.getId(),
                pet.getName(),
                species,
                primaryBreed,
                secondaryBreed,
                primaryColor,
                secondaryColor,
                tertiaryColor,
                pet.getGender(),
                pet.getCoat(),
                pet.getSize(),
                pet.getAge(),
                pet.getDescription(),
                pet.getImageUrl(),
                pet.getIsAdopted(),
                pet.getAttributes(),
                pet.getDateAdded(),
                organization
        );
    }
}
