package com.petadoption.center.model;

import com.petadoption.center.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "species")
    private PetSpecies petSpecies;

    @Column(name = "primary_breed")
    private PetBreed primaryBreed;

    @Column(name = "secondary_breed")
    private PetBreed secondaryBreed;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "primary", column = @Column(name = "pet_color_primary")),
            @AttributeOverride( name = "secondary", column = @Column(name = "pet_color_secondary")),
            @AttributeOverride( name = "tertiary", column = @Column(name = "pet_color_tertiary"))
    })
    private PetColor color;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    @Enumerated(EnumType.STRING)
    private Coats coat;

    @Enumerated(EnumType.STRING)
    private Sizes size;

    @Enumerated(EnumType.STRING)
    private Ages age;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    private Boolean isAdopted;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "sterilized", column = @Column(name = "pet_sterilized")),
            @AttributeOverride( name = "vaccinated", column = @Column(name = "pet_vaccinated")),
            @AttributeOverride( name = "chipped", column = @Column(name = "pet_chipped")),
            @AttributeOverride( name = "specialNeeds", column = @Column(name = "pet_special_needs")),
            @AttributeOverride( name = "houseTrained", column = @Column(name = "pet_house_trained")),
            @AttributeOverride( name = "goodWithKids", column = @Column(name = "pet_good_with_kids")),
            @AttributeOverride( name = "goodWithDogs", column = @Column(name = "pet_good_with_dogs")),
            @AttributeOverride( name = "goodWithCats", column = @Column(name = "pet_good_with_cats"))
    })
    private PetAttribute attributes;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    private Organization organization;
}
