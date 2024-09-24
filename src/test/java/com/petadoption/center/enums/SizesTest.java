package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.PetDescriptionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class SizesTest {

    @Test
    @DisplayName("Test if the proper Size is returned by the valid description")
    void testIfTheProperSizeIsReturnedByTheValidDescription() throws PetDescriptionException {

        String[] descriptionsArray = {"Small", "Medium", "Large", "Extra Large"};

        Sizes[] sizesArray = new Sizes[4];
        for (int i = 0; i < descriptionsArray.length; i++) {
            sizesArray[i] = Sizes.getSizeByDescription(descriptionsArray[i]);
        }

        assertEquals(Sizes.SMALL, sizesArray[0]);
        assertEquals(Sizes.MEDIUM, sizesArray[1]);
        assertEquals(Sizes.LARGE, sizesArray[2]);
        assertEquals(Sizes.EXTRA_LARGE, sizesArray[3]);
    }

    @Test
    @DisplayName("Test if the proper Size is returned by the description, while ignoring the case")
    void testIfTheProperSizeIsReturnedByTheDescriptionIgnoringCase() throws PetDescriptionException {

        String[] descriptionsArray = {"SMALL", "medium", "LaRGe", "ExTRa LarGE"};

        Sizes[] sizesArray = new Sizes[4];
        for (int i = 0; i < descriptionsArray.length; i++) {
            sizesArray[i] = Sizes.getSizeByDescription(descriptionsArray[i]);
        }

        assertEquals(Sizes.SMALL, sizesArray[0]);
        assertEquals(Sizes.MEDIUM, sizesArray[1]);
        assertEquals(Sizes.LARGE, sizesArray[2]);
        assertEquals(Sizes.EXTRA_LARGE, sizesArray[3]);
    }

    @Test
    @DisplayName("Test if PetDescriptionException is thrown when the description is invalid")
    void testIfPetDescriptionExceptionIsThrownWhenDescriptionIsInvalid() {

        String description = "invalid";

        assertThrows(PetDescriptionException.class, () -> Sizes.getSizeByDescription(description));
    }
}
