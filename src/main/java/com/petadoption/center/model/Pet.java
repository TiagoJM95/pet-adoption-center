package com.petadoption.center.model;

import com.petadoption.center.enums.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

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

    private LocalDate dateAdded;

    private Organization organization;
}
