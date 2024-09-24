package com.petadoption.center.enums;

import com.petadoption.center.exception.status.InvalidStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class StatusTest {

    @Test
    @DisplayName("Test if the proper Status is returned by the valid description")
    void testIfTheProperStatusIsReturnedByTheValidDescription() throws InvalidStatusException {

        String[] descriptionsArray = {"Pending", "Rejected", "Accepted", "Form Requested", "Form Filled"};

        Status[] statusArray = new Status[5];
        for (int i = 0; i < descriptionsArray.length; i++) {
            statusArray[i] = Status.getStatusByDescription(descriptionsArray[i]);
        }

        assertEquals(Status.PENDING, statusArray[0]);
        assertEquals(Status.REJECTED, statusArray[1]);
        assertEquals(Status.ACCEPTED, statusArray[2]);
        assertEquals(Status.FORM_REQUESTED, statusArray[3]);
        assertEquals(Status.FORM_FILLED, statusArray[4]);
    }

    @Test
    @DisplayName("Test if the proper Status is returned by the description, while ignoring the case")
    void testIfTheProperStatusIsReturnedByTheDescriptionIgnoringCase() throws InvalidStatusException {

        String[] descriptionsArray = {"PENDING", "reJECted", "AcCEpted", "FORM requested", "Form FiLLed"};

        Status[] statusArray = new Status[5];
        for (int i = 0; i < descriptionsArray.length; i++) {
            statusArray[i] = Status.getStatusByDescription(descriptionsArray[i]);
        }

        assertEquals(Status.PENDING, statusArray[0]);
        assertEquals(Status.REJECTED, statusArray[1]);
        assertEquals(Status.ACCEPTED, statusArray[2]);
        assertEquals(Status.FORM_REQUESTED, statusArray[3]);
        assertEquals(Status.FORM_FILLED, statusArray[4]);
    }

    @Test
    @DisplayName("Test if InvalidStatusException is thrown when the description is invalid")
    void testIfInvalidStatusExceptionIsThrownWhenDescriptionIsInvalid() {

        String description = "invalid";

        assertThrows(InvalidStatusException.class, () -> Status.getStatusByDescription(description));
    }
}
