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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BreedConverterTest {

    Species species = new Species("123123-12312312-3123", "Dog");
    SpeciesGetDto speciesGetDto = new SpeciesGetDto("123123-12312312-3123", "Dog");

    @Test
    @DisplayName("Test BreedCreateDto to Breed model is working correctly")
    void fromBreedCreateDtoToModel() {


        BreedCreateDto breedCreateDto = new BreedCreateDto(
                "Golden Retriever",
                "123123-12312312-3123"
        );

        Breed breed = BreedConverter.toModel(breedCreateDto);

        assertEquals("Golden Retriever", breed.getName());
    }

    @Test
    @DisplayName("Test if BreedGetDto to Breed model is working correctly")
    void fromBreedGetDtoToModel() {

        BreedGetDto breedGetDto = new BreedGetDto(
                "123123-12312312-3333",
                "Labrador Retriever",
                speciesGetDto
        );

        Breed breed = BreedConverter.toModel(breedGetDto);

        assertEquals("123123-12312312-3333", breed.getId());
        assertEquals("Labrador Retriever", breed.getName());
        assertEquals("Dog", breed.getSpecies().getName());
    }


    @Test
    @DisplayName("Test Breed model to BreedGetDto is working correctly")
    void fromModelToBreedGetDto() {

        Breed breed = new Breed("123123-12312312-3333", "Labrador Retriever", species);

        BreedGetDto breedGetDto = BreedConverter.toDto(breed);

        assertEquals("123123-12312312-3333", breedGetDto.id());
        assertEquals("Labrador Retriever", breedGetDto.name());
        assertEquals("Dog", breedGetDto.speciesDto().name());
    }

}
