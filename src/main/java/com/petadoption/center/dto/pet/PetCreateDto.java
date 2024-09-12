package com.petadoption.center.dto.pet;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.petadoption.center.util.Messages.*;

public record PetCreateDto(

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
        String name,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String petSpeciesId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String primaryBreedId,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String secondaryBreedId,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String primaryColor,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String secondaryColor,

        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String tertiaryColor,

        @NotBlank(message = BLANK_FIELD)
        String gender,

        @NotBlank(message = BLANK_FIELD)
        String coat,

        @NotBlank(message = BLANK_FIELD)
        String size,

        @NotBlank(message = BLANK_FIELD)
        String age,

        @NotBlank(message = BLANK_FIELD)
        String description,

        @NotBlank(message = BLANK_FIELD)
        String imageUrl,

        Boolean isAdopted,

        Boolean petSterilized,

        Boolean petVaccinated,

        Boolean petChipped,

        Boolean specialNeeds,

        Boolean houseTrained,

        Boolean goodWithKids,

        Boolean goodWithDogs,

        Boolean goodWithCats,

        @NotBlank(message = BLANK_FIELD)
        @Pattern(regexp = "[a-zA-Z0-9-]+", message = ONLY_LETTERS)
        String organizationId
) {
        @JsonCreator
        public PetCreateDto(
                @JsonProperty("name") String name,
                @JsonProperty("petSpeciesId") String petSpeciesId,
                @JsonProperty("primaryBreedId") String primaryBreedId,
                @JsonProperty("secondaryBreedId") String secondaryBreedId,
                @JsonProperty("primaryColor") String primaryColor,
                @JsonProperty("secondaryColor") String secondaryColor,
                @JsonProperty("tertiaryColor") String tertiaryColor,
                @JsonProperty("gender") String gender,
                @JsonProperty("coat") String coat,
                @JsonProperty("size") String size,
                @JsonProperty("age") String age,
                @JsonProperty("description") String description,
                @JsonProperty("imageUrl") String imageUrl,
                @JsonProperty("isAdopted") Boolean isAdopted,
                @JsonProperty("petSterilized") Boolean petSterilized,
                @JsonProperty("petVaccinated") Boolean petVaccinated,
                @JsonProperty("petChipped") Boolean petChipped,
                @JsonProperty("specialNeeds") Boolean specialNeeds,
                @JsonProperty("houseTrained") Boolean houseTrained,
                @JsonProperty("goodWithKids") Boolean goodWithKids,
                @JsonProperty("goodWithDogs") Boolean goodWithDogs,
                @JsonProperty("goodWithCats") Boolean goodWithCats,
                @JsonProperty("organizationId") String organizationId) {

                this.name = name;
                this.petSpeciesId = petSpeciesId;
                this.primaryBreedId = primaryBreedId;
                this.secondaryBreedId = secondaryBreedId != null ? secondaryBreedId : "NONE"; // Default value
                this.primaryColor = primaryColor;
                this.secondaryColor = secondaryColor != null ? secondaryColor : "NONE"; // Default value
                this.tertiaryColor = tertiaryColor != null ? tertiaryColor : "NONE"; // Default value
                this.gender = gender;
                this.coat = coat;
                this.size = size;
                this.age = age;
                this.description = description;
                this.imageUrl = imageUrl;
                this.isAdopted = isAdopted;
                this.petSterilized = petSterilized;
                this.petVaccinated = petVaccinated;
                this.petChipped = petChipped;
                this.specialNeeds = specialNeeds;
                this.houseTrained = houseTrained;
                this.goodWithKids = goodWithKids;
                this.goodWithDogs = goodWithDogs;
                this.goodWithCats = goodWithCats;
                this.organizationId = organizationId;
        }
}