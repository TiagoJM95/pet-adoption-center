package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.petadoption.center.testUtils.TestDtoFactory.*;
import static com.petadoption.center.testUtils.TestEntityFactory.createBreed;
import static com.petadoption.center.testUtils.TestEntityFactory.createSpecies;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BreedConverterTest {

    Species species = createSpecies() ;
    SpeciesGetDto speciesGetDto = createSpeciesGetDto();


    @Test
    @DisplayName("Test BreedCreateDto to Breed model is working correctly")
    void fromBreedCreateDtoToModel() {

        Breed breed = BreedConverter.toModel(breedCreateDto(species.getId()));

        assertEquals("Golden Retriever", breed.getName());
    }

    @Test
    @DisplayName("Test if BreedGetDto to Breed model is working correctly")
    void fromBreedGetDtoToModel() {

        Breed breed = BreedConverter.toModel(createBreedGetDto(speciesGetDto));

        assertEquals("2222-2222-3333", breed.getId());
        assertEquals("Golden Retriever", breed.getName());
        assertEquals("Dog", breed.getSpecies().getName());
    }


    @Test
    @DisplayName("Test Breed model to BreedGetDto is working correctly")
    void fromModelToBreedGetDto() {

        BreedGetDto breedGetDto = BreedConverter.toDto(createBreed(species));

        assertEquals("222222-22222222-2222", breedGetDto.id());
        assertEquals("Labrador", breedGetDto.name());
        assertEquals("Dog", breedGetDto.speciesDto().name());
    }

}
