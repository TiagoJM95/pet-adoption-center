package com.petadoption.center.converter;


import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.Species;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class SpeciesConverterTest {

    @Test
    @DisplayName("Test SpeciesCreateDto to Species model is working correctly")
    void fromSpeciesCreateDtoToModel() {

        SpeciesCreateDto speciesCreateDto = new SpeciesCreateDto("Cat");

        Species species = SpeciesConverter.fromSpeciesCreateDtoToModel(speciesCreateDto);

        assertEquals("Cat", species.getName());
    }

    @Test
    @DisplayName("Test Species model to SpeciesGetDto is working correctly")
    void fromModelToSpeciesGetDto() {

        Species species = new Species(1L, "Cat");

        SpeciesGetDto speciesGetDto = SpeciesConverter.fromModelToSpeciesGetDto(species);

        assertEquals(1L, speciesGetDto.id());
        assertEquals("Cat", speciesGetDto.name());
    }
}
