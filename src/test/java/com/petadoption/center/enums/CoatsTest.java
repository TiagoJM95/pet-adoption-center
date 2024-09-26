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
public class CoatsTest {

    @Test
    @DisplayName("Test if the proper Coat is returned by the valid description")
    void testIfTheProperCoatIsReturnedByTheValidDescription() throws PetDescriptionException {

        String[] descriptionsArray = {"Hairless", "Short", "Medium", "Long"};

        Coats[] coatsArray = new Coats[4];
        for (int i = 0; i < descriptionsArray.length; i++) {
            coatsArray[i] = Coats.getCoatByDescription(descriptionsArray[i]);
        }

        assertEquals(Coats.HAIRLESS, coatsArray[0]);
        assertEquals(Coats.SHORT, coatsArray[1]);
        assertEquals(Coats.MEDIUM, coatsArray[2]);
        assertEquals(Coats.LONG, coatsArray[3]);
    }

    @Test
    @DisplayName("Test if the proper Coat is returned by the description, while ignoring the case")
    void testIfTheProperCoatIsReturnedByTheDescriptionIgnoringCase() throws PetDescriptionException {

        String[] descriptionsArray = {"HAIRLESS", "ShOrt", "mEdiUm", "long"};

        Coats[] coatsArray = new Coats[5];
        for (int i = 0; i < descriptionsArray.length; i++) {
            coatsArray[i] = Coats.getCoatByDescription(descriptionsArray[i]);
        }

        assertEquals(Coats.HAIRLESS, coatsArray[0]);
        assertEquals(Coats.SHORT, coatsArray[1]);
        assertEquals(Coats.MEDIUM, coatsArray[2]);
        assertEquals(Coats.LONG, coatsArray[3]);
    }

    @Test
    @DisplayName("Test if PetDescriptionException is thrown when the description is invalid")
    void testIfPetDescriptionExceptionIsThrownWhenDescriptionIsInvalid() {

        String description = "invalid";

        assertThrows(PetDescriptionException.class, () -> Coats.getCoatByDescription(description));
    }

}
