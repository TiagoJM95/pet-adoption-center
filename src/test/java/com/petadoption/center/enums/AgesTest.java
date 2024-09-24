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
public class AgesTest {

    @Test
    @DisplayName("Test if the proper Age is returned by the valid description")
    void testIfTheProperAgeIsReturnedByTheValidDescription() throws PetDescriptionException {

        String[] descriptionsArray = {"Baby", "Young", "Adult", "Senior", "Unknown"};

        Ages[] agesArray = new Ages[5];
        for (int i = 0; i < descriptionsArray.length; i++) {
            agesArray[i] = Ages.getAgeByDescription(descriptionsArray[i]);
        }

        assertEquals(Ages.BABY, agesArray[0]);
        assertEquals(Ages.YOUNG, agesArray[1]);
        assertEquals(Ages.ADULT, agesArray[2]);
        assertEquals(Ages.SENIOR, agesArray[3]);
        assertEquals(Ages.UNKNOWN, agesArray[4]);
    }

    @Test
    @DisplayName("Test if the proper Age is returned by the description, while ignoring the case")
    void testIfTheProperAgeIsReturnedByTheDescriptionIgnoringCase() throws PetDescriptionException {

        String[] descriptionsArray = {"BABY", "YoUnG", "adult", "SeNIor", "UnKNOwn"};

        Ages[] agesArray = new Ages[5];
        for (int i = 0; i < descriptionsArray.length; i++) {
            agesArray[i] = Ages.getAgeByDescription(descriptionsArray[i]);
        }

        assertEquals(Ages.BABY, agesArray[0]);
        assertEquals(Ages.YOUNG, agesArray[1]);
        assertEquals(Ages.ADULT, agesArray[2]);
        assertEquals(Ages.SENIOR, agesArray[3]);
        assertEquals(Ages.UNKNOWN, agesArray[4]);
    }

    @Test
    @DisplayName("Test if PetDescriptionException is thrown when the description is invalid")
    void testIfPetDescriptionExceptionIsThrownWhenDescriptionIsInvalid() {

        String description = "invalid";

        assertThrows(PetDescriptionException.class, () -> Ages.getAgeByDescription(description));
    }
}