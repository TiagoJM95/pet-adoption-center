package com.petadoption.center.enums;

import com.petadoption.center.exception.pet.PetDescriptionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class GendersTest {

    @Test
    @DisplayName("Test if the proper Gender is returned by the valid description")
    void testIfTheProperGenderIsReturnedByTheValidDescription() throws PetDescriptionException {

        String[] descriptionsArray = {"Male", "Female"};

        Genders[] gendersArray = new Genders[2];
        for (int i = 0; i < descriptionsArray.length; i++) {
            gendersArray[i] = Genders.getGenderByDescription(descriptionsArray[i]);
        }

        assertEquals(Genders.MALE, gendersArray[0]);
        assertEquals(Genders.FEMALE, gendersArray[1]);
    }

    @Test
    @DisplayName("Test if the proper Gender is returned by the description, while ignoring the case")
    void testIfTheProperGenderIsReturnedByTheDescriptionIgnoringCase() throws PetDescriptionException {

        String[] descriptionsArray = {"MALE", "fEMAle"};

        Genders[] gendersArray = new Genders[2];
        for (int i = 0; i < descriptionsArray.length; i++) {
            gendersArray[i] = Genders.getGenderByDescription(descriptionsArray[i]);
        }

        assertEquals(Genders.MALE, gendersArray[0]);
        assertEquals(Genders.FEMALE, gendersArray[1]);
    }

    @Test
    @DisplayName("Test if PetDescriptionException is thrown when the description is invalid")
    void testIfPetDescriptionExceptionIsThrownWhenDescriptionIsInvalid() {

        String description = "invalid";

        assertThrows(PetDescriptionException.class, () -> Genders.getGenderByDescription(description));
    }
}
