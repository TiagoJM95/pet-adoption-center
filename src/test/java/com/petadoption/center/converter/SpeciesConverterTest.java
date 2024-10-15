package com.petadoption.center.converter;


import com.petadoption.center.dto.species.SpeciesCreateDto;
import com.petadoption.center.dto.species.SpeciesGetDto;
import com.petadoption.center.model.Species;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.petadoption.center.testUtils.TestDtoFactory.speciesCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.speciesGetDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createSpecies;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SpeciesConverterTest {

    @Test
    @DisplayName("Test SpeciesCreateDto to Species model is working correctly")
    void fromSpeciesCreateDtoToModel() {

        Species species = SpeciesConverter.toModel(speciesCreateDto());

        assertEquals("Dog", species.getName());
    }

    @Test
    @DisplayName("Test if SpeciesGetDto to Species model is working correctly")
    void fromSpeciesGetDtoToModel() {

        Species species = SpeciesConverter.toModel(speciesGetDto());

        assertEquals("111111-11111111-1111", species.getId());
        assertEquals("Dog", species.getName());
    }

    @Test
    @DisplayName("Test Species model to SpeciesGetDto is working correctly")
    void fromModelToSpeciesGetDto() {

        SpeciesGetDto speciesGetDto = SpeciesConverter.toDto(createSpecies());

        assertEquals("111111-11111111-1111", speciesGetDto.id());
        assertEquals("Dog", speciesGetDto.name());
    }

    @Test
    @DisplayName("Test if SpeciesCreateDto to species returns null if received null dto")
    void testIfFromSpeciesCreateDtoReturnNullIfReceivedNullDto() {
        assertNull(SpeciesConverter.toModel((SpeciesCreateDto) null));
    }

    @Test
    @DisplayName("Test if SpeciesGetDto to species returns null if received null dto")
    void testIfFromSpeciesGetDtoReturnNullIfReceivedNullDto() {
        assertNull(SpeciesConverter.toModel((SpeciesGetDto) null));
    }

    @Test
    @DisplayName("Test if Species to SpeciesGetDto returns null if received null model")
    void testIfFromModelReturnNullIfReceivedNullModel() {
        assertNull(SpeciesConverter.toDto(null));
    }
}
