package com.petadoption.center.converter;


import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
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
public class SpeciesConverterTest {

    @Test
    @DisplayName("Test SpeciesCreateDto to Species model is working correctly")
    void fromSpeciesCreateDtoToModel() {

        SpeciesCreateDto speciesCreateDto = new SpeciesCreateDto("Cat");

        Species species = SpeciesConverter.toModel(speciesCreateDto);

        assertEquals("Cat", species.getName());
    }

    @Test
    @DisplayName("Test if SpeciesGetDto to Species model is working correctly")
    void fromSpeciesGetDtoToModel() {

        SpeciesGetDto speciesGetDto = new SpeciesGetDto(
                "2313-21321-31231",
                "Cat");

        Species species = SpeciesConverter.toModel(speciesGetDto);

        assertEquals("2313-21321-31231", species.getId());
        assertEquals("Cat", species.getName());
    }

    @Test
    @DisplayName("Test Species model to SpeciesGetDto is working correctly")
    void fromModelToSpeciesGetDto() {

        Species species = new Species("2313-21321-31231", "Cat");

        SpeciesGetDto speciesGetDto = SpeciesConverter.toDto(species);

        assertEquals("2313-21321-31231", speciesGetDto.id());
        assertEquals("Cat", speciesGetDto.name());
    }
}
