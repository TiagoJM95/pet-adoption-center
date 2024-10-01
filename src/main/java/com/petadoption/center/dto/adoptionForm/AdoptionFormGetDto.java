package com.petadoption.center.dto.adoptionForm;

import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.user.UserGetDto;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AdoptionFormGetDto(
        @Schema(
                description = "Adoption form id",
                example = "12789-1234-1234-12345")
        String id,

        @Schema(
                description = "User information",
                example = "{ \"userId\": \"12789-1234-1234-12345\", \"firstName\": \"Manuel\", \"lastName\": \"Silva\", \"email\": \"email@email.com\", \"nif\": \"123456789\", \"dateOfBirth\": \"1990-01-01\", \"phoneNumber\": \"918765432\", \"address\": { \"street\": \"Rua dos vizinhos, 132\", \"postalCode\": \"4410-222\", \"city\": \"Vila Nova de Gaia\", \"state\": \"Porto\" } }", type = "UserGetDto")
        UserGetDto user,

        @Schema(
                description = "Pet information",
                example = "{ \"petId\": \"12789-1234-1234-12345\", \"name\": \"Rex\", \"specie\": { \"id\": \"12789-1234-1234-12345\", \"name\": \"Dog\" }, \"primaryBreed\": { \"id\": \"12789-1234-1234-12345\", \"name\": \"Husky\" }, \"secondaryBreed\": { \"id\": \"12789-1234-1234-12345\", \"name\": \"German Shepherd\" }, \"primaryColor\": { \"id\": \"12789-1234-1234-12345\", \"name\": \"Black\" }, \"secondaryColor\": { \"id\": \"12789-1234-1234-12345\", \"name\": \"White\" }, \"tertiaryColor\": { \"id\": \"12789-1234-1234-12345\", \"name\": \"Brown\" }, \"gender\": \"Male\", \"coat\": \"Short\", \"size\": \"Small\", \"age\": \"Adult\", \"description\": \"Cute and friendly\", \"imageUrl\": \"https://www.example.com\", \"isAdopted\": true, \"attributes\": { \"sterilized\": true, \"vaccinated\": true, \"chipped\": true, \"specialNeeds\": false, \"houseTrained\": true, \"goodWithKids\": true, \"goodWithDogs\": true, \"goodWithCats\": true } }")
        PetGetDto pet,

        Family userFamily,

        @Schema(
                description = "Vacation home for the pet",
                example = "House in the countryside")
        String petVacationHome,

        @Schema(
                description = "Is the user responsible for the pet?",
                example = "true")
        Boolean isResponsibleForPet,

        @Schema(
                description = "Any additional notes",
                example = "The pet is a little shy around strangers.")
        String otherNotes,

        @Schema(
                description = "Pet's address",
                example = "{ \"street\": \"Rua das Flores, 45\", \"postalCode\": \"4510-333\", \"city\": \"Lisbon\", \"state\": \"Lisbon\" }")
        Address petAddress
) {
}
