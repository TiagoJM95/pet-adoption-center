package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.color.ColorGetDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.pet.PetDescriptionException;
import com.petadoption.center.model.*;
import com.petadoption.center.model.embeddable.Attributes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.petadoption.center.testUtils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class PetConverterTest {

    Species species = createSpecies();
    SpeciesGetDto speciesGetDto = createSpeciesGetDto();
    Breed primaryBreed = createPrimaryBreed(species);
    BreedGetDto primaryBreedDto = createPrimaryBreedDto(speciesGetDto);
    Breed secondaryBreed = createSecondaryBreed(species);
    BreedGetDto secondaryBreedDto = createSecondaryBreedDto(speciesGetDto);
    Color primaryColor = createPrimaryColor();
    ColorGetDto primaryColorDto = createPrimaryColorDto();
    Color secondaryColor = createSecondaryColor();
    ColorGetDto secondaryColorDto = createSecondaryColorDto();
    Color tertiaryColor = createTertiaryColor();
    ColorGetDto tertiaryColorDto = createTertiaryColorDto();
    Organization organization = createOrganization();
    OrgGetDto orgGetDto = createOrgGetDto();
    Attributes attributes = createAttributes();


    @Test
    @DisplayName("Test if PetCreateDto to Pet model is working correctly")
    void testIfPetCreateDtoToPetModelIsWorkingCorrectly() {
        PetCreateDto petCreateDto = PetCreateDto.builder()
                .name("Max")
                .petSpeciesId(species.getId())
                .primaryBreedId(primaryBreed.getId())
                .secondaryBreedId(secondaryBreed.getId())
                .primaryColor(primaryColor.getId())
                .secondaryColor(secondaryColor.getId())
                .tertiaryColor(tertiaryColor.getId())
                .gender("Male")
                .coat("Short")
                .size("Medium")
                .age("Young")
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationId(organization.getId())
                .build();

        Pet pet = PetConverter.toModel(petCreateDto);

        assertEquals("Max", pet.getName());
        assertEquals("Max is a very friendly dog", pet.getDescription());
        assertEquals("https://www.dogimages.com", pet.getImageUrl());
        assertEquals(false, pet.isAdopted());
        assertEquals(attributes, pet.getAttributes());
    }

    @Test
    @DisplayName("Test if PetGetDto to Pet model is working correctly")
    void testIfPetGetDtoToPetModelIsWorkingCorrectly() throws PetDescriptionException {
        PetGetDto petGetDto = PetGetDto.builder()
                .id("88888-88888888-8888")
                .name("Max")
                .speciesDto(speciesGetDto)
                .primaryBreedDto(primaryBreedDto)
                .secondaryBreedDto(secondaryBreedDto)
                .primaryColorDto(primaryColorDto)
                .secondaryColorDto(secondaryColorDto)
                .tertiaryColorDto(tertiaryColorDto)
                .gender(Genders.getGenderByDescription("Male"))
                .coat(Coats.getCoatByDescription("Short"))
                .size(Sizes.getSizeByDescription("Medium"))
                .age(Ages.getAgeByDescription("Young"))
                .description("Max is a very friendly dog")
                .imageUrl("https://www.dogimages.com")
                .isAdopted(false)
                .attributes(attributes)
                .organizationDto(orgGetDto)
                .build();

        Pet pet = PetConverter.toModel(petGetDto);

        assertEquals("88888-88888888-8888", pet.getId());
        assertEquals(species, pet.getSpecies());
        assertEquals(primaryBreed, pet.getPrimaryBreed());
        assertEquals(secondaryBreed, pet.getSecondaryBreed());
        assertEquals(primaryColor, pet.getPrimaryColor());
        assertEquals(secondaryColor, pet.getSecondaryColor());
        assertEquals(tertiaryColor, pet.getTertiaryColor());
        assertEquals("Max is a very friendly dog", pet.getDescription());
        assertEquals("https://www.dogimages.com", pet.getImageUrl());
        assertEquals(false, pet.isAdopted());
        assertEquals(attributes, pet.getAttributes());
        assertEquals(organization, pet.getOrganization());
    }
}