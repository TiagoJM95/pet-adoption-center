package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate dateOfBirth;

    private String address;

    private String postalCode;

    private String phoneCountryCode;

    private Integer phoneNumber;

    private List<Pet> favoritePets;

    private List<Pet> adoptedPets;

    private List<AdoptionForm> adoptionForms;
}
