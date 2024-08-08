package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    private String address;

    private List<Pet> favoritePets;

    private List<Pet> adoptedPets;

    private List<AdoptionForm> adoptionForms;
}
