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

    private Species species;

    private PetBreed petBreed;

    private PetColor petColor;

    private Genders gender;

    private Coats coat;

    private Sizes size;

    private Ages age;

    private String description;

    private String imageUrl;

    private Boolean isAdopted;

    private PetAttribute attributes;

    private LocalDate dateAdded;

    private Organization organization;

}
