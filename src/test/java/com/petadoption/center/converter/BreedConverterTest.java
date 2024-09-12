package com.petadoption.center.converter;

import com.petadoption.center.dto.breed.BreedCreateDto;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Species;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BreedConverterTest {

    Species species = new Species(1L, "Dog");

    @Test
    @DisplayName("Test BreedCreateDto to Breed model is working correctly")
    void fromBreedCreateDtoToModel() {


        BreedCreateDto breedCreateDto = new BreedCreateDto(
                "Golden Retriever",
                1L
        );

        Breed breed = BreedConverter.fromBreedCreateDtoToModel(breedCreateDto, species);

        assertEquals("Golden Retriever", breed.getName());
        assertEquals(1L, breed.getSpecies().getId());
    }


    @Test
    @DisplayName("Test Breed model to BreedGetDto is working correctly")
    void fromModelToBreedGetDto() {

        Breed breed = new Breed(1L, "Labrador Retriever", species);

        BreedGetDto breedGetDto = BreedConverter.fromModelToBreedGetDto(breed);

        assertEquals(1L, breedGetDto.id());
        assertEquals("Labrador Retriever", breedGetDto.name());
        assertEquals("Dog", breedGetDto.specie());
    }

}
