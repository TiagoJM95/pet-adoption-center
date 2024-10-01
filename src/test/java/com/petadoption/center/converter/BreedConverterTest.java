package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedCreateDto;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BreedConverterTest {

    Species species = createSpecies() ;
    SpeciesGetDto speciesGetDto = speciesGetDto();


    @Test
    @DisplayName("Test BreedCreateDto to Breed model is working correctly")
    void fromBreedCreateDtoToModel() {

        Breed breed = BreedConverter.toModel(breedCreateDto(species.getId()));

        assertEquals("Golden Retriever", breed.getName());
    }

    @Test
    @DisplayName("Test if BreedGetDto to Breed model is working correctly")
    void fromBreedGetDtoToModel() {

        Breed breed = BreedConverter.toModel(primaryBreedGetDto(speciesGetDto));

        assertEquals("222222-22222222-2222", breed.getId());
        assertEquals("Labrador", breed.getName());
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

    @Test
    @DisplayName("Test if BreedCreateDto to model returns null if received null dto")
    void testIfFromBreedCreateDtoToModelReturnNullIfReceivedNullDto() {
        assertNull(BreedConverter.toModel((BreedCreateDto) null));
    }

    @Test
    @DisplayName("Test if BreedGetDto to model returns null if received null dto")
    void testIfFromBreedGetDtoToModelReturnNullIfReceivedNullDto() {
        assertNull(BreedConverter.toModel((BreedGetDto) null));
    }

    @Test
    @DisplayName("Test if Breed model to BreedGetDto returns null if received null model")
    void testIfFromModelToBreedGetDtoReturnNullIfReceivedNullModel() {
        assertNull(BreedConverter.toDto(null));
    }

}
